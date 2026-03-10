package project;

import java.util.HashMap;
import java.util.Map;

public class StockName {

    public static final Map<String, String> NAME = new HashMap<>();

    static {

        // ★ KOSPI 대형주
        NAME.put("005930", "삼성전자");
        NAME.put("000660", "SK하이닉스");
        NAME.put("207940", "삼성바이오로직스");
        NAME.put("051910", "LG화학");
        NAME.put("006400", "삼성SDI");
        NAME.put("035420", "NAVER");
        NAME.put("005380", "현대차");
        NAME.put("105560", "KB금융");
        NAME.put("012330", "현대모비스");
        NAME.put("028260", "삼성물산");
        NAME.put("055550", "신한지주");
        NAME.put("034730", "SK");
        NAME.put("033780", "KT&G");
        NAME.put("017670", "SK텔레콤");
        NAME.put("090430", "아모레퍼시픽");
        NAME.put("096770", "SK이노베이션");

        NAME.put("003550", "LG");
        NAME.put("018260", "삼성에스디에스");
        NAME.put("029780", "삼성카드");
        NAME.put("024110", "기업은행");
        NAME.put("009150", "삼성전기");
        NAME.put("030200", "KT");
        NAME.put("010130", "고려아연");
        NAME.put("011170", "롯데케미칼");

        NAME.put("003490", "대한항공");
        NAME.put("015760", "한국전력");
        NAME.put("036570", "엔씨소프트");
        NAME.put("051900", "LG생활건강");
        NAME.put("000270", "기아");
        NAME.put("000810", "삼성화재");
        NAME.put("000720", "현대건설");
        NAME.put("003670", "포스코홀딩스");
        NAME.put("047050", "POSCO인터내셔널");
        NAME.put("161390", "한국타이어앤테크놀로지");
        NAME.put("307950", "현대오토에버");
        NAME.put("066570", "LG전자");
        NAME.put("003230", "삼양식품");
        NAME.put("002790", "아모레G");
        NAME.put("009830", "한화솔루션");
        NAME.put("086790", "하나금융지주");

        // ★ 2차전지
        NAME.put("373220", "LG에너지솔루션");
        NAME.put("096530", "씨티씨바이오");
        NAME.put("361610", "SK아이이테크놀로지");
        NAME.put("381970", "금양");
        NAME.put("383220", "초록뱀미디어");

        // ★ 반도체/IT
        NAME.put("012030", "DB");
        NAME.put("326030", "SK바이오팜");
        NAME.put("108320", "LX세미콘");
        NAME.put("322000", "현대에너지솔루션");
        NAME.put("330860", "네패스아크");
        NAME.put("042700", "한미반도체");
        NAME.put("112040", "위메이드");
        NAME.put("205100", "엑셈");
        NAME.put("046890", "서울반도체");

        // ★ 자동차
        NAME.put("011760", "현대위아");
        NAME.put("090350", "노랑풍선");
        NAME.put("214150", "클래시스");
        NAME.put("000040", "KR모터스");
        NAME.put("097950", "CJ제일제당");

        // ★ 바이오/의료
        NAME.put("068270", "셀트리온");
        NAME.put("128940", "한미약품");
        NAME.put("302440", "SK바이오사이언스");
        NAME.put("003090", "대한약품공업");
        NAME.put("086890", "하이비젼시스템");
        NAME.put("005690", "대상");
        NAME.put("225330", "씨엠에스에듀");
        NAME.put("185750", "종근당");
        NAME.put("011200", "HMM");
        NAME.put("064550", "바이오니아");

        NAME.put("005490", "POSCO홀딩스");
        NAME.put("010950", "S-Oil");
        NAME.put("009540", "한국조선해양");
        NAME.put("003490", "대한항공");
        NAME.put("000880", "한화");
        NAME.put("000120", "CJ대한통운");
        NAME.put("051600", "한화솔루션");
        NAME.put("011780", "금호석유");
        NAME.put("009420", "한올바이오파마");
        NAME.put("241560", "두산밥캣");

        NAME.put("001040", "CJ");
        NAME.put("001450", "현대해상");
        NAME.put("008930", "한미사이언스");
        NAME.put("034020", "두산에너빌리티");
        NAME.put("004020", "현대제철");
        NAME.put("005940", "NH투자증권");
        NAME.put("003000", "부광약품");
        NAME.put("002380", "KCC");
        NAME.put("006260", "LS");
        NAME.put("004990", "롯데지주");

        NAME.put("007070", "GS리테일");
        NAME.put("161890", "한국콜마");
        NAME.put("241590", "화승엔터프라이즈");
        NAME.put("006800", "미래에셋증권");
        NAME.put("003620", "쌍용C&E");
        NAME.put("006360", "GS건설");
        NAME.put("002790", "아모레G");
        NAME.put("000860", "삼성물산우");
        NAME.put("000100", "유한양행");
        NAME.put("011070", "LG이노텍");

        NAME.put("090080", "애경산업");
        NAME.put("004370", "농심");
        NAME.put("097520", "엠씨넥스");
        NAME.put("004000", "롯데정밀화학");
        NAME.put("001790", "대한제당");
        NAME.put("145720", "덴티움");
        NAME.put("042660", "대우조선해양");
        NAME.put("002270", "롯데칠성");
        NAME.put("138040", "메리츠금융지주");
        NAME.put("030000", "제일기획");

        NAME.put("298050", "효성첨단소재");
        NAME.put("298020", "효성티앤씨");
        NAME.put("004800", "효성");
        NAME.put("002810", "삼영무역");
        NAME.put("071050", "한국금융지주");
        NAME.put("010120", "LS일렉트릭");
        NAME.put("012450", "한화에어로스페이스");
        NAME.put("000990", "DB하이텍");
        NAME.put("001230", "동국제강");
        NAME.put("001250", "GS글로벌");

        NAME.put("089790", "제이티");
        NAME.put("214320", "이노션");
        NAME.put("298040", "효성중공업");
        NAME.put("267270", "현대중공업지주");
        NAME.put("267260", "현대중공업");
        NAME.put("272210", "한화시스템");
        NAME.put("003490", "대한항공");
        NAME.put("006120", "SK디스커버리");
        NAME.put("000240", "한국철강");
        NAME.put("357250", "에코프로");

        NAME.put("247540", "에코프로비엠");
        NAME.put("086520", "에코프로에이치엔");
        NAME.put("371460", "TIGER 차이나전기차 ETF");
        NAME.put("136480", "하림");
        NAME.put("230360", "에코마케팅");
        NAME.put("030190", "NICE평가정보");
        NAME.put("067160", "아프리카TV");
        NAME.put("253450", "스튜디오드래곤");
        NAME.put("251270", "넷마블");
        NAME.put("018880", "한온시스템");

        NAME.put("073240", "금호타이어");
        NAME.put("035760", "CJ ENM");
        NAME.put("377300", "카카오페이");
        NAME.put("000020", "동화약품");
        NAME.put("009310", "참엔지니어링");
        NAME.put("243840", "신흥에스이씨");
        NAME.put("048260", "오스템임플란트");
        NAME.put("036010", "아비코전자");
        NAME.put("039490", "키움증권");
        NAME.put("058470", "리노공업");

        NAME.put("196170", "알테오젠");
        NAME.put("003380", "하림지주");
        NAME.put("027740", "마니커");
        NAME.put("017800", "현대엘리베이터");
        NAME.put("017960", "한국카본");
        NAME.put("001745", "대한전선");
        NAME.put("033500", "동성화학");
        NAME.put("011930", "신성이엔지");
        NAME.put("271560", "오리온");
        NAME.put("005850", "에스엘");

        NAME.put("138490", "코오롱플라스틱");
        NAME.put("383310", "에코프로머티");
        NAME.put("196490", "디에이테크놀로지");
        NAME.put("078930", "GS");
        NAME.put("010060", "OCI홀딩스");
        NAME.put("009605", "태영건설우");
        NAME.put("096300", "베트남개발1");
        NAME.put("340120", "베이직하우스");
        NAME.put("078600", "대주전자재료");
        NAME.put("067630", "에이치엘비");

    }
    
    public static String getName(String code) {
        return NAME.get(code); // null이면 null 반환, 미등록종목 표시 안함
    }
}