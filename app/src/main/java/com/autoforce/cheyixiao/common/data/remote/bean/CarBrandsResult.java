package com.autoforce.cheyixiao.common.data.remote.bean;

import java.util.List;

/**
 * Created by xialihao on 2018/11/28.
 */
public class CarBrandsResult extends SimpleResult{


    /**
     * code : 200
     * results : [{"bid":0,"bname":"全部"},{"bid":1,"bname":"大众"},{"bid":3,"bname":"丰田"},{"bid":8,"bname":"福特"},{"bid":9,"bname":"克莱斯勒"},{"bid":10,"bname":"雷诺"},{"bid":11,"bname":"菲亚特"},{"bid":12,"bname":"现代"},{"bid":13,"bname":"标致"},{"bid":14,"bname":"本田"},{"bid":15,"bname":"宝马"},{"bid":19,"bname":"荣威"},{"bid":20,"bname":"MG"},{"bid":22,"bname":"中华"},{"bid":24,"bname":"哈飞"},{"bid":25,"bname":"吉利汽车"},{"bid":26,"bname":"奇瑞"},{"bid":27,"bname":"北京"},{"bid":32,"bname":"东风"},{"bid":33,"bname":"奥迪"},{"bid":34,"bname":"阿尔法罗密欧"},{"bid":35,"bname":"阿斯顿·马丁"},{"bid":36,"bname":"奔驰"},{"bid":37,"bname":"布加迪"},{"bid":38,"bname":"别克"},{"bid":39,"bname":"宾利"},{"bid":40,"bname":"保时捷"},{"bid":41,"bname":"道奇"},{"bid":42,"bname":"法拉利"},{"bid":44,"bname":"捷豹"},{"bid":45,"bname":"smart"},{"bid":46,"bname":"Jeep"},{"bid":47,"bname":"凯迪拉克"},{"bid":48,"bname":"兰博基尼"},{"bid":49,"bname":"路虎"},{"bid":50,"bname":"路特斯"},{"bid":51,"bname":"林肯"},{"bid":52,"bname":"雷克萨斯"},{"bid":53,"bname":"铃木"},{"bid":54,"bname":"劳斯莱斯"},{"bid":56,"bname":"MINI"},{"bid":57,"bname":"玛莎拉蒂"},{"bid":58,"bname":"马自达"},{"bid":60,"bname":"讴歌"},{"bid":61,"bname":"帕加尼"},{"bid":62,"bname":"起亚"},{"bid":63,"bname":"日产"},{"bid":65,"bname":"斯巴鲁"},{"bid":67,"bname":"斯柯达"},{"bid":68,"bname":"三菱"},{"bid":69,"bname":"双龙"},{"bid":70,"bname":"沃尔沃"},{"bid":71,"bname":"雪佛兰"},{"bid":72,"bname":"雪铁龙"},{"bid":73,"bname":"英菲尼迪"},{"bid":74,"bname":"中兴"},{"bid":75,"bname":"比亚迪"},{"bid":76,"bname":"长安"},{"bid":77,"bname":"长城"},{"bid":78,"bname":"猎豹汽车"},{"bid":79,"bname":"昌河"},{"bid":80,"bname":"力帆汽车"},{"bid":81,"bname":"东南"},{"bid":82,"bname":"广汽传祺"},{"bid":83,"bname":"金杯"},{"bid":84,"bname":"江淮"},{"bid":86,"bname":"海马"},{"bid":87,"bname":"华泰"},{"bid":88,"bname":"陆风"},{"bid":91,"bname":"红旗"},{"bid":93,"bname":"永源"},{"bid":94,"bname":"众泰"},{"bid":95,"bname":"奔腾"},{"bid":96,"bname":"福田"},{"bid":97,"bname":"黄海"},{"bid":99,"bname":"威兹曼"},{"bid":100,"bname":"科尼赛克"},{"bid":101,"bname":"开瑞"},{"bid":102,"bname":"威麟"},{"bid":108,"bname":"广汽吉奥"},{"bid":109,"bname":"KTM"},{"bid":110,"bname":"一汽"},{"bid":111,"bname":"野马汽车"},{"bid":112,"bname":"GMC"},{"bid":113,"bname":"东风风神"},{"bid":114,"bname":"五菱汽车"},{"bid":117,"bname":"AC Schnitzer"},{"bid":118,"bname":"Lorinser"},{"bid":119,"bname":"江铃"},{"bid":120,"bname":"宝骏"},{"bid":122,"bname":"启辰"},{"bid":124,"bname":"理念"},{"bid":129,"bname":"迈凯伦"},{"bid":130,"bname":"纳智捷"},{"bid":133,"bname":"特斯拉"},{"bid":140,"bname":"巴博斯"},{"bid":141,"bname":"福迪"},{"bid":142,"bname":"东风小康"},{"bid":143,"bname":"北汽威旺"},{"bid":144,"bname":"依维柯"},{"bid":145,"bname":"金龙"},{"bid":146,"bname":"欧朗"},{"bid":150,"bname":"海格"},{"bid":151,"bname":"九龙"},{"bid":152,"bname":"观致"},{"bid":154,"bname":"北汽制造"},{"bid":155,"bname":"上汽大通"},{"bid":161,"bname":"腾势"},{"bid":162,"bname":"思铭"},{"bid":163,"bname":"长安商用"},{"bid":164,"bname":"恒天"},{"bid":165,"bname":"东风风行"},{"bid":167,"bname":"五十铃"},{"bid":168,"bname":"摩根"},{"bid":169,"bname":"DS"},{"bid":173,"bname":"北汽绅宝"},{"bid":174,"bname":"如虎"},{"bid":175,"bname":"金旅"},{"bid":181,"bname":"哈弗"},{"bid":182,"bname":"之诺"},{"bid":184,"bname":"华骐"},{"bid":187,"bname":"东风风度"},{"bid":192,"bname":"潍柴英致"},{"bid":196,"bname":"成功汽车"},{"bid":197,"bname":"福汽启腾"},{"bid":199,"bname":"卡威"},{"bid":202,"bname":"泰卡特"},{"bid":203,"bname":"北汽幻速"},{"bid":204,"bname":"陆地方舟"},{"bid":205,"bname":"赛麟"},{"bid":206,"bname":"知豆"},{"bid":208,"bname":"北汽新能源"},{"bid":210,"bname":"江铃集团轻汽"},{"bid":213,"bname":"南京金龙"},{"bid":214,"bname":"凯翼"},{"bid":219,"bname":"全球鹰"},{"bid":220,"bname":"华颂"},{"bid":222,"bname":"乔治·巴顿"},{"bid":231,"bname":"宝沃"},{"bid":232,"bname":"御捷"},{"bid":235,"bname":"前途"},{"bid":241,"bname":"LOCAL MOTORS"},{"bid":245,"bname":"华凯"},{"bid":259,"bname":"东风风光"},{"bid":260,"bname":"华泰新能源"},{"bid":263,"bname":"驭胜"},{"bid":267,"bname":"汉腾汽车"},{"bid":269,"bname":"斯威汽车"},{"bid":270,"bname":"江铃集团新能源"},{"bid":271,"bname":"比速汽车"},{"bid":272,"bname":"ARCFOX"},{"bid":276,"bname":"ALPINA"},{"bid":279,"bname":"LYNK&CO"},{"bid":280,"bname":"电咖"},{"bid":282,"bname":"福田乘用车"},{"bid":283,"bname":"WEY"},{"bid":284,"bname":"蔚来"},{"bid":286,"bname":"云度"},{"bid":291,"bname":"威马汽车"},{"bid":294,"bname":"长安轻型车"},{"bid":296,"bname":"瑞驰新能源"},{"bid":297,"bname":"君马汽车"},{"bid":298,"bname":"宇通客车"},{"bid":299,"bname":"长安跨越"},{"bid":301,"bname":"北汽道达"},{"bid":304,"bname":"国金汽车"},{"bid":306,"bname":"鑫源"},{"bid":307,"bname":"裕路"},{"bid":308,"bname":"Polestar"},{"bid":312,"bname":"庆铃汽车"},{"bid":317,"bname":"云雀汽车"},{"bid":319,"bname":"捷途"},{"bid":326,"bname":"东风·瑞泰特"},{"bid":329,"bname":"广汽集团"},{"bid":336,"bname":"红星汽车"},{"bid":337,"bname":"容大智造"}]
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * bid : 0
         * bname : 全部
         */

        private Integer bid;
        private String bname;

        public ResultsBean() {
        }

        public ResultsBean(Integer bid, String bname) {
            this.bid = bid;
            this.bname = bname;
        }

        public Integer getBid() {
            return bid;
        }

        public void setBid(Integer bid) {
            this.bid = bid;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }
    }
}
