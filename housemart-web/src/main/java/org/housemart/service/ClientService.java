package org.housemart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.housemart.dao.entities.ClientNotificationEntity;
import org.housemart.dao.entities.ClientNotificationSendEntity;
import org.housemart.dao.entities.ClientRegisterEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;
import org.housemart.framework.push.BaiduYunAPI;
import org.housemart.framework.push.JavaPNSProvider;
import org.json.JSONObject;

public class ClientService {

	@SuppressWarnings("rawtypes")
	private GenericDao clientNotificationDao = SpringContextHolder.getBean("clientNotificationDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder.getBean("clientRegisterDao");
	
	
	public void processClientNotifications()
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", ClientNotificationEntity.StatusEnum.Unprocessed.value);
		map.put("currentTime", new Date());
		List<ClientNotificationEntity> list = (List<ClientNotificationEntity>)clientNotificationDao.select("findClientNotificationList", map);
		
		for (ClientNotificationEntity notification : list)
		{
			this.processClientNotification(notification.getId());
		}
	}
	
	public void processClientNotification(int id)
	{
		ClientNotificationEntity notification = (ClientNotificationEntity)clientNotificationDao.load("loadClientNotificationById", id);
		
		if (notification != null && notification.getStatus().equals(ClientNotificationEntity.StatusEnum.Unprocessed.value))
		{
			this.processClientNotification(notification);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void processClientNotification(ClientNotificationEntity notification)
	{
		if (notification.getStatus().equals(ClientNotificationEntity.StatusEnum.Processed.value))
		{
			return;
		}
		
		notification.setStatus(ClientNotificationEntity.StatusEnum.Processed.value);
		clientNotificationDao.update("updateClientNotification", notification);
		
		ClientNotificationSendEntity notificationSend;
		
		String clientUIDs = "";
		List<ClientRegisterEntity> clientList = new ArrayList<ClientRegisterEntity>();
		List<ClientRegisterEntity> brokerList = new ArrayList<ClientRegisterEntity>();
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("hasToken", true);
		
		if (notification.getTarget().equals(ClientNotificationEntity.TargetEnum.Custom.value))
		{
			String[] clients = notification.getClientUIDs().split(",");
			
			for (int i = 0; i < clients.length; i++)
			{
				clientUIDs += (clientUIDs.length() == 0 ? "" : ",") + ("'" + clients[i].trim() + "'");
			}
			
			para.put("clientIDs", clientUIDs);
			
			para.put("toBroker", true);
			brokerList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
			
			para.remove("toBroker");
			para.put("toClient", true);
			clientList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
		}
		else if (notification.getTarget().equals(ClientNotificationEntity.TargetEnum.Broker.value))
		{
			para.put("toBroker", clientUIDs);
			para.put("device", "iphone");
			brokerList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
		}
		else if (notification.getTarget().equals(ClientNotificationEntity.TargetEnum.Client.value))
		{
			para.put("toClient", clientUIDs);
			para.put("device", "iphone");
			clientList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
		}
		else
		{
			para.put("device", "iphone");
			para.put("toBroker", true);
			brokerList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
			
			para.remove("toBroker");
			para.put("toClient", true);
			clientList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
		}
		
		//for APNS
		//broker
		int count = 0;
		clientUIDs = "";
		for (int i = 0; i < brokerList.size(); i++)
		{
			ClientRegisterEntity broker = brokerList.get(i);
			
			if (broker.getDevice().toLowerCase().contains("iphone"))
			{
				clientUIDs += (clientUIDs.length() == 0 ? "" : ",") + broker.getClientID();
				count++;
				
				if (count == 20)
				{
					notificationSend = new ClientNotificationSendEntity();
					notificationSend.setNotificationID(notification.getId());
					notificationSend.setClientUIDs(clientUIDs);
					notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.APNSBroker.value);
					notificationSend.setStatus(ClientNotificationSendEntity.StatusEnum.Unprocessed.value);
					
					clientNotificationDao.add("addClientNotificationSend", notificationSend);
					
					clientUIDs = "";
					count = 0;
				}
			}
		}
		if (clientUIDs.length() > 0)
		{
			notificationSend = new ClientNotificationSendEntity();
			notificationSend.setNotificationID(notification.getId());
			notificationSend.setClientUIDs(clientUIDs);
			notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.APNSBroker.value);
			notificationSend.setStatus(ClientNotificationSendEntity.StatusEnum.Unprocessed.value);
			
			clientNotificationDao.add("addClientNotificationSend", notificationSend);
		}
		//client
		count = 0;
		clientUIDs = "";
		for (int i = 0; i < clientList.size(); i++)
		{
			ClientRegisterEntity client = clientList.get(i);
			
			if (client.getDevice().toLowerCase().contains("iphone"))
			{
				clientUIDs += (clientUIDs.length() == 0 ? "" : ",") + client.getClientID();
				count++;
				
				if (count == 20)
				{
					notificationSend = new ClientNotificationSendEntity();
					notificationSend.setNotificationID(notification.getId());
					notificationSend.setClientUIDs(clientUIDs);
					notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.APNSClient.value);
					notificationSend.setStatus(ClientNotificationSendEntity.StatusEnum.Unprocessed.value);
					
					clientNotificationDao.add("addClientNotificationSend", notificationSend);
					
					clientUIDs = "";
					count = 0;
				}
			}
		}
		if (clientUIDs.length() > 0)
		{
			notificationSend = new ClientNotificationSendEntity();
			notificationSend.setNotificationID(notification.getId());
			notificationSend.setClientUIDs(clientUIDs);
			notificationSend.setStatus(ClientNotificationSendEntity.StatusEnum.Unprocessed.value);
			notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.APNSClient.value);
			
			clientNotificationDao.add("addClientNotificationSend", notificationSend);
		}
		
		//for Android
		notificationSend = new ClientNotificationSendEntity();
		notificationSend.setNotificationID(notification.getId());
		notificationSend.setStatus(ClientNotificationSendEntity.StatusEnum.Unprocessed.value);
		notificationSend.setClientUIDs("");
		if (notification.getTarget().equals(ClientNotificationEntity.TargetEnum.Broker.value))
		{
			notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.BaiduYunBroker.value);
			clientNotificationDao.add("addClientNotificationSend", notificationSend);
		}
		else if (notification.getTarget().equals(ClientNotificationEntity.TargetEnum.Client.value))
		{
			notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.BaiduYunClient.value);
			clientNotificationDao.add("addClientNotificationSend", notificationSend);
		}
		else if (notification.getTarget().equals(ClientNotificationEntity.TargetEnum.All.value))
		{
			notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.BaiduYun.value);
			clientNotificationDao.add("addClientNotificationSend", notificationSend);
		}
		else
		{
			//Custom Target
			String androidClients = "";
			String androidBrokers = "";
			for (ClientRegisterEntity client : clientList)
			{
				if (!client.getDevice().toLowerCase().contains("iphone"))
				{
					if (client.getBrokerID() != null)
					{
						androidBrokers += (androidBrokers.length() == 0 ? "" : ",") + client.getClientID();
					}
					else
					{
						androidClients += (androidClients.length() == 0 ? "" : ",") + client.getClientID();
					}
				}
			}
			
			if (androidClients.length() > 0)
			{
				notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.BaiduYunClient.value);
				notificationSend.setClientUIDs(androidClients);
				clientNotificationDao.add("addClientNotificationSend", notificationSend);
			}
			
			if (androidBrokers.length() > 0)
			{
				notificationSend.setSendType(ClientNotificationSendEntity.SendTypeEnum.BaiduYunBroker.value);
				notificationSend.setClientUIDs(androidBrokers);
				clientNotificationDao.add("addClientNotificationSend", notificationSend);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void sendClientNotifications()
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", ClientNotificationSendEntity.StatusEnum.Unprocessed.value);
		List<ClientNotificationSendEntity> list = (List<ClientNotificationSendEntity>)clientNotificationDao.select("findClientNotificationSendList", map);
		
		String ids = "";
		for (ClientNotificationSendEntity notificationSend : list)
		{
			ids += (ids.length() == 0 ? "" : ",") + notificationSend.getId();
		}
		
		if (ids.length() > 0)
		{
			map = new HashMap<String,Object>();
			map.put("status", ClientNotificationSendEntity.StatusEnum.Processing.value);
			map.put("processTime", new Date());
			map.put("ids", ids);
			clientNotificationDao.update("updateClientNotificationSendByMap", map);
		}
		
		List<Map<String, String>> clientMessageList  = new ArrayList<Map<String, String>>();
		List<Map<String, String>> brokerMessageList  = new ArrayList<Map<String, String>>();
		
		for (ClientNotificationSendEntity notificationSend : list)
		{
			Map<String, String> result = this.sendClientNotification(notificationSend, false);
			
			if (result != null)
			{
				if (result.get("target").equals("client"))
				{
					clientMessageList.add(result);
				}
				else
				{
					brokerMessageList.add(result);
				}
			}
		}
		
		if (clientMessageList.size() > 0)
		{
			JavaPNSProvider.pushMessageToAPNS(clientMessageList.toArray(), true);
		}
		
		if (brokerMessageList.size() > 0)
		{
			JavaPNSProvider.pushMessageToAPNS(brokerMessageList.toArray(), false);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> sendClientNotification(ClientNotificationSendEntity notificationSend, boolean skipIOS)
	{
		notificationSend.setProcessTime(new Date());
		notificationSend.setStatus(ClientNotificationSendEntity.StatusEnum.Processed.value);
		clientNotificationDao.update("updateClientNotificationSend", notificationSend);
		
		String clientUIDs = "";
		String[] clients = notificationSend.getClientUIDs().split(",");
		
		for (int i = 0; i < clients.length; i++)
		{
			clientUIDs += (clientUIDs.length() == 0 ? "" : ",") + ("'" + clients[i].trim() + "'");
		}
		
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("clientIDs", clientUIDs);
		para.put("hasToken", true);
		
		List<ClientRegisterEntity> clientList = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", para);
		
		Map<String, String> result = null;
		
		if (clientList.size() > 0)
		{
			for (ClientRegisterEntity client : clientList)
			{
				try
				{	
					if (client.getDevice() == null)
					{
					}
					else if (client.getClientToken() != null && client.getDevice().toLowerCase().contains("iphone"))
					{
						if (skipIOS)
						{
							result = new HashMap<String, String>();
							result.put("deviceToken", client.getClientToken());
							result.put("content", notificationSend.getNotificationContent());
							result.put("toClient", (client.getBrokerID() == null || client.getBrokerID() == -1) ? "client" : "broker");
						}
						else
						{
							JavaPNSProvider.pushMessageToAPNS(client.getClientToken(), notificationSend.getNotificationContent(), 1, client.getBrokerID() == null || client.getBrokerID() == -1);
						}
					}
					else if (client.getClientToken() != null)
					{
						if (client.getClientToken().contains("|"))
						{
							String[] token = client.getClientToken().split("\\|");
							
							JSONObject msgObj = new JSONObject();
							msgObj.put("title", "好识买通知");
							msgObj.put("description", notificationSend.getNotificationContent());
							String content = msgObj.toString();
							BaiduYunAPI.pushMessage2Android(token[0], token[1], 1, content);
						}
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		else
		{
			try
			{
				JSONObject msgObj = new JSONObject();
				msgObj.put("title", "好识买通知");
				msgObj.put("description", notificationSend.getNotificationContent());
				String content = msgObj.toString();
				
				if (notificationSend.getSendType().equals(ClientNotificationSendEntity.SendTypeEnum.BaiduYun.value) ||
					notificationSend.getSendType().equals(ClientNotificationSendEntity.SendTypeEnum.BaiduYunBroker.value))
				{
					BaiduYunAPI.pushMessage2Android(1, content, true);
				}
				
				if (notificationSend.getSendType().equals(ClientNotificationSendEntity.SendTypeEnum.BaiduYun.value) ||
					notificationSend.getSendType().equals(ClientNotificationSendEntity.SendTypeEnum.BaiduYunClient.value))
				{
					BaiduYunAPI.pushMessage2Android(1, content, false);
				}
			}
			catch(Exception e)
			{
			}
		}
		
		return result;
	}
}
