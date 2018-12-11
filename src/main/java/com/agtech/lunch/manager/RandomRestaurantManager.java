package com.agtech.lunch.manager;

import com.agtech.lunch.enums.Restaurant;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018-11-30
 * Time: 15:46
 */
@Component
public class RandomRestaurantManager {

    private List<String> aArray = new ArrayList<String>() {{
        add("快到饭点了，在下掐指一算，今天宜吃%s");
        add("还在犹豫今天吃什么？%s走起");
        add("该去%s看服务员小姐姐了");
        add("我想看你们吃%s了，记得发照片");
    }};

    private List<String> bArray = new ArrayList<String>() {{
        add("，但今天是%s，不考虑%s吗？");
        add("，慢着，今天%s，%s啊！");
        add("，不过%s一般都是%s安排");
        add("---才怪，%s整%s");
    }};

    private List<String> miandanCandidates = new ArrayList<>();

    private List<String> cArray = new ArrayList<String>() {{
        add("一日一度的看脸免单，参加的@我，格式\"@午饭君 我参加！\"，10分钟之内赶紧报名哈！");
    }};

    public String getRestaurantResult() {
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Shanghai"));
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        Random r = new Random();
        int totalWeight = Arrays.stream(Restaurant.values()).map(re -> re.getWeight()).reduce((w1, w2) -> w1 + w2).get();
        int lot = r.nextInt(totalWeight);
        Restaurant re = null;

        for(int i = 0; i < Restaurant.values().length; i++) {
            re = Restaurant.values()[i];
            lot = lot - re.getWeight();
            if(lot < 0) {
                break;
            }
        }

        return makeSentence(re.getName(), dayOfWeek);
    }

    public String getMiandanBeginMessage() {
        return cArray.get(0);
    }

    public String getMiandanEndMessage() {
        String retMsg = "今日已截止，参加免单的人有：" + miandanCandidates.toString();
        if(miandanCandidates.size() <= 3) {
            retMsg += ", 由于参加人数不足4人，活动取消。。";
        }
        return retMsg;
    }

    public String getMiandanLuckMessage() {
        Random r = new Random();
        int index = r.nextInt(miandanCandidates.size());
        String luckyName = miandanCandidates.get(index);
        return "今日免单：" + luckyName + "！";
    }

    public void addMiandanList(String name) {
        if(!miandanCandidates.contains(name)) {
            miandanCandidates.add(name);
        }
    }

    public void clearMiandanList() {
        miandanCandidates.clear();
    }

    private String makeSentence(String restName, DayOfWeek dayOfWeek) {
        Random ra = new Random();
        String a = aArray.get(ra.nextInt(aArray.size()));
        String b = bArray.get(ra.nextInt(bArray.size()));

        String retStr = String.format(a, restName);
        String alterStr = "";
        if(dayOfWeek.getValue() == DayOfWeek.WEDNESDAY.getValue() && !restName.equals(Restaurant.XIAOHENGSHUIJIAO.getName())) {
            alterStr = String.format(b, "星期三", Restaurant.XIAOHENGSHUIJIAO.getName());
        } else if(dayOfWeek.getValue() == DayOfWeek.FRIDAY.getValue() && !restName.equals(Restaurant.GUOLING.getName())) {
            alterStr = String.format(b, "星期五", Restaurant.GUOLING.getName());
        }

        return retStr + alterStr;
    }
}
