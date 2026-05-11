package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaqAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // FAQ
        List<Map<String, String>> faqList = new ArrayList<>();
        faqList.add(createFaq("공통", "JELLY는 어떤 서비스인가요?",
        		"JELLY 쉽게 구매하기 어려운 한정판 상품을 판매하고 거래할 수 있는 서비스이며 전문가의 철저한 검수를 통해 안전하고 신속하게 거래할 수 있습니다."));
        
        faqList.add(createFaq("구매", "상품 구매는 어떻게 하나요?",
                "서비스 가입 후 로그인 한 회원은 누구나 상품을 구매할 수 있습니다.\n상품을 검색하고, 원하는 사이즈를 선택한 후 구매 버튼을 누르면 즉시 구매 혹은 구매 입찰이 가능합니다.\n즉시 구매하거나 입찰 후 거래가 체결되면 판매자가 상품을 검수 센터로 전달하여 검수를 진행하게 되며, 검수를 합격한 상품은 구매자에게 안전하게 배송됩니다."));
        
        faqList.add(createFaq("구매", "'구매 입찰'과 '즉시 구매'의 차이는 무엇인가요?",
                "'구매 입찰'은 희망하는 가격으로 입찰가를 등록하여 거래가 가능하며,\n등록하신 구매 입찰 가격으로 상품을 판매할 판매자가 나타나면 거래가 이루어집니다.\n'즉시 구매'는 원하시는 상품의 옵션을 선택하여 즉시 구매가 가능합니다.\n(가장 낮은 판매 입찰가에 상품을 구매할 수 있습니다.)"));

        faqList.add(createFaq("구매", "구매 상품 배송일정이 궁금해요",
                "JELLY의 검수기준에 합격한 상품은 순차적으로 배송 준비 후 택배사를 통해 배송이 시작됩니다.\n\n- 일반 배송 : 거래 체결 후 배송, 5-7일 내 도착 예정 (일요일・공휴일 제외)\n- 빠른 배송 : 거래 체결 후 배송, 1-2일 내 도착 예정 (일요일・공휴일 제외)\n* 통상적인 배송 기간이며 검수 과정 및 택배사 물량 상황에 따라 변동될 수 있습니다."));

        faqList.add(createFaq("구매", "지정일 배송이 가능한가요?",
                "JELLY의 검수센터는 많은 거래 상품의 입고/검수/출고 작업을 진행하고 있습니다.\n검수센터 및 택배사 진행 상황에 따라 소요 일정이 다를 수 있으며,\n별도의 일정을 지정하여 검수와 배송을 도와드리기 어려운 점 양해 부탁드립니다."));

        faqList.add(createFaq("구매", "구매를 취소하고 싶어요.",
                "거래 대기 후(거래 체결일 기준) 15분 이내에 '구매 결정' 의사 선택이 가능하며,\n계정당 1일 1회 '구매 거부' 할 수 있습니다.\n[경로] - JELLY 앱 : 주문 내역 상세 보기 > 오른쪽 상단 • • • (더 보기) 클릭 > '구매 거부' 선택"));


        faqList.add(createFaq("공통", "구매와 판매는 어떤 방식으로 이루어지나요?",
                "구매자와 판매자가 각각 원하는 구매/판매 가격으로 입찰할 수 있으며, 가격이 일치하면 거래가 체결됩니다.\n또한, 구매자는 판매자의 입찰 중 가장 낮은 가격으로 즉시 구매할 수 있으며, 판매자는 구매자의 입찰 중 가장 높은 가격으로 즉시 판매할 수 있습니다."));

        faqList.add(createFaq("공통", "JELLY의 검수가 궁금해요.",
                "업계 전문가로 구성된 검수팀이 소재, 사이즈, 바느질, 접착 등을 꼼꼼하게 분석하여 정가품 판정을 진행하게 됩니다.\n검수에 합격한 상품은 JELLY 검수택(Tag)이 부착되며, 상품 수령 후 검수택을 제거하거나 손상시킨 경우에 대해서는 JELLY에서 정품 인증을 보장하지 않습니다."));

        faqList.add(createFaq("공통", "검수는 얼마나 걸리나요?",
                "거래가 체결되어 검수센터로 입고 완료된 상품은 검수센터에서 최대한 빠르게 검수가 진행될 수 있도록 노력하고 있습니다.\n평균적으로 입고일 제외한 3영업일 이내 검수가 진행되며 상품별 또는 검수센터 진행 상황에 따라 유동적으로 진행됩니다."));

        faqList.add(createFaq("판매", "판매 과정을 설명해주세요.",
                "서비스 가입 후 로그인 한 회원은 상품을 판매할 수 있습니다.\n판매하실 상품을 검색하고, 판매할 사이즈 버튼을 누르면 보관 신청 혹은 선불 발송으로 선택이 가능하여 판매 시 아래 내용을 참고 부탁드립니다.\n'보관 신청'의 경우 거래가 체결되기 전에 미리 상품을 발송하고, 검수 완료된 상품에 한하여 창고에 보관한 뒤 별도 판매 입찰을 통해 거래가 체결되면 판매 대금을 정산해드립니다.\n'선불 발송'의 경우 창고 보관 없이 즉시 판매하거나 입찰 후 거래가 체결되면 상품을 검수 센터로 발송하고, 검수에 합격하면 판매 대금을 정산해드립니다."));

        faqList.add(createFaq("이용정책", "커뮤니티 가이드라인",
        	    "JELLY 내 STYLE 서비스는 회원들의 일상 속 스니커즈, 의류 등의 패션 스타일을 공유하여 서로 영감을 얻을 수 있는 공간입니다.\n모두가 건강하고 즐겁게 사용할 수 있는 공간을 만들기 위해 함께 노력해주세요.\n\n커뮤니티 가이드라인은 서비스를 안전하게 유지하고 보호하기 위해 만들어진 정책입니다. JELLY를 사용하면 이 가이드라인과 이용약관에 동의하게 됩니다.\n이 가이드라인을 위반하는 경우 콘텐츠가 삭제되거나, 계정이 비활성화되는 등의 제재를 받을 수 있습니다."));

        faqList.add(createFaq("이용정책", "부적절행위 금지",
        	    "아래에 해당하는 경우, 이용약관 제 7조(이용제한 등), 21조(부적절 행위)에 따라 일시정지나 영구이용정지 조치됩니다.\n허위사실 유포 관련 고의성이 확인될 시, 유관부서로 이관되어 처리될 수 있습니다.\n\n- 가품 거래 시도\n- 사용감 있는 상품 거래 시도\n- 여러 개의 계정을 생성하여 자전거래\n- 결제 혜택 및 포인트 획득, 시세 조작 등의 목적으로 지인과 공모한 허위 거래\n- 신용카드 불법 현금 유통 (소위 카드깡)\n- 개인 정보를 타인에게 유출하거나 결제 카드를 대여하여 거래(가족, 친지 포함)\n- 부당한 목적으로 협의하여 계정이나 명의를 도용하여 부정거래(온라인, 오프라인 포함)\n- 커뮤니티 내 허위사실 유포 등 서비스 운영을 방해하는 각종 부정행위"));

        faqList.add(createFaq("판매", "보관 판매 페널티 규정은 어떻게 되나요?",
        	    "보관 판매 시, 케이스에 따라 보증금이 환불되지 않거나 페널티가 부과됩니다.\n\n[검수센터에 도착하지 않은 경우]\n- 발송지연, 미입고, 신청 1시간 이후 취소: 보증금 미환불\n\n[검수센터에 도착한 경우]\n- 착불 발송: 최종 정산 금액에서 택배비를 차감하고 입금\n- 가품, 사용감: 해당 상품 신청일시 기준으로 마지막 거래 체결 이전 30일 평균 거래가의 15%\n- 보관비 결제 회피 행위: 보관비 미결제 상태일 경우 판매 및 회수가 불가하며 결제 후 판매/회수 진행이 가능합니다."));
        
        	
        int pageSize = 10; // 한 페이지에 표시할 항목 
        int totalItems = faqList.size(); // 전체 항목 개
        int totalPages = (int) Math.ceil((double) totalItems / pageSize); // 전체 페이지 수

        // 현재 페이지 계산 (기본값 1, 없으면 1)
        int currentPage = 1;
        String pageParam = request.getParameter("currentPage");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        // 현재 페이지에 해당하는 데이터
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);
        List<Map<String, String>> paginatedFaqList = faqList.subList(startIndex, endIndex);

        // JSP에 데이터 전달
        request.setAttribute("faqList", paginatedFaqList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        return "/views/notice/faq.jsp";
    }

    private Map<String, String> createFaq(String category, String question, String answer) {
        Map<String, String> faq = new HashMap<>();
        faq.put("category", category);
        faq.put("question", question);
        faq.put("answer", answer);
        return faq;
    }
}