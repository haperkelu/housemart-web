package org.housemart.util;

import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.web.beans.HouseRoomType;

public class ZrHouseHelper {
    public static final float oneDollarToRMB = 6.3f;

    public static ZrHouseDetail parseZrHouseDetail(String detailString) {
	ZrHouseDetail zrHouseDetail = new ZrHouseDetail();
	if (StringUtils.isNotBlank(detailString)) {
	    // 老格式
	    String[] info = detailString.split(",\n");
	    if (info.length > 1) {
		for (int i = 0; i < info.length; i++) {
		    if (StringUtils.isNotBlank(info[i])) {
			String inf = info[i].replaceAll("\n", " ").trim();
			if (inf.indexOf("N/A") > -1) {
			    continue;
			}

			if (inf.indexOf("bed") > -1) {
			    zrHouseDetail.setBed(Integer.valueOf(StringUtils
				    .substringBefore(inf, "bed").trim()));
			} else if (inf.indexOf("bath") > -1
				&& inf.indexOf(" bath") == -1) {
			    zrHouseDetail.setBath(Integer.valueOf(StringUtils
				    .substringBefore(inf, "bath").trim()));
			} else if (inf.indexOf(" bath") > -1) {
			} else if (inf.indexOf("sq") > -1) {
			    zrHouseDetail.setAreaFeet(Integer
				    .valueOf(StringUtils.substringBefore(inf,
					    "sq").replaceAll(",", "")));
			} else if (inf.indexOf("Built") > -1) {
			    zrHouseDetail.setBuildYear(Integer
				    .valueOf(StringUtils.substringAfter(inf,
					    "Built").trim()));
			} else {
			    zrHouseDetail.setType(inf);
			}

		    }
		}
	    } else {
		// 新格式
		info = detailString.split(" ");
		try {
		    zrHouseDetail.setBed(Integer.valueOf(info[0].trim()));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		try {
		    zrHouseDetail.setBath(Integer.valueOf(info[2].trim()));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		try {
		    zrHouseDetail.setAreaFeet(Integer.valueOf(info[4]
			    .replaceAll(",", "").trim()));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		try {
		    zrHouseDetail
			    .setBuildYear(Integer.valueOf(info[18].trim()));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		try {
		    zrHouseDetail.setType(info[11].trim());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	return zrHouseDetail;
    }

    public static HouseExtEntity zrHouse2HMHouse(ZrHouse zrHouse) {
	HouseExtEntity house = new HouseExtEntity();
	if (zrHouse != null) {
	    ZrHouseDetail zrHouseDetail = parseZrHouseDetail(zrHouse
		    .getHouseDetail());

	    HouseRoomType roomType = new HouseRoomType();
	    roomType.setShi(zrHouseDetail.getBed());
	    roomType.setTing(zrHouseDetail.getBed());
	    roomType.setWei(zrHouseDetail.getBath());
	    roomType.setYangtai(zrHouseDetail.getBed());
	    if (roomType.getShi() != null) {
		Integer[] rTypeArray = roomType.toArray();
		house.setRoomType(arrayToInteger(rTypeArray, rTypeArray.length));
	    }

	    float area = (float) (zrHouseDetail.getAreaFeet() * 0.0929);
	    house.setPropertyArea(area);

	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.YEAR, zrHouseDetail.getBuildYear());
	    calendar.set(Calendar.MONTH, 0);
	    calendar.set(Calendar.DATE, 1);
	    house.setBuildTime(calendar.getTime());

	    String dollarPrice = zrHouse.getPrice();
	    if (StringUtils.isNotBlank(dollarPrice)) {
		dollarPrice = dollarPrice.replaceAll(",", "").replaceAll("[$]",
			"");
		house.setSalePrice(Float.valueOf(dollarPrice) * oneDollarToRMB);
	    }

	    house.setSaleMemo("税费各付");

	    if (zrHouseDetail.getType().equalsIgnoreCase("Single Family")) {
		house.setHouseType("7"); // 独栋
		house.setFloor(6);
	    } else if (zrHouseDetail.getType().equalsIgnoreCase("Condo")) {
		house.setHouseType("4"); // 公寓
		house.setFloor(3);
	    } else if (zrHouseDetail.getType().equalsIgnoreCase("Townhouse")) {
		house.setHouseType("2"); // 联排
		house.setFloor(8);
	    } else {
		house.setHouseType("7"); // 独栋
		house.setFloor(6);
	    }
	}
	return house;
    }

    public static class ZrHouseDetail {
	int bed;
	int bath;
	int buildYear;
	int areaFeet;
	String type;

	public int getBed() {
	    return bed;
	}

	public void setBed(int bed) {
	    this.bed = bed;
	}

	public int getBath() {
	    return bath;
	}

	public void setBath(int bath) {
	    this.bath = bath;
	}

	public int getBuildYear() {
	    return buildYear;
	}

	public void setBuildYear(int buildYear) {
	    this.buildYear = buildYear;
	}

	public int getAreaFeet() {
	    return areaFeet;
	}

	public void setAreaFeet(int areaFeet) {
	    this.areaFeet = areaFeet;
	}

	public String getType() {
	    return type;
	}

	public void setType(String type) {
	    this.type = type;
	}
    }

    /**
     * [1, 0, 0, 1, 1] -> 10011
     * 
     * @param array
     * @param length
     * @return
     */
    protected static Integer arrayToInteger(Integer[] array, int length) {
	Integer result = null;
	if (ArrayUtils.isNotEmpty(array) && array.length == length) {
	    result = 0;
	    for (Integer elem : array) {
		elem = elem == null ? 0 : elem;
		result = result * 10 + elem;
	    }
	}
	return result;
    }
}
