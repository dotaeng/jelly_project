$(document).ready(function () {
  const $filterMenu = $("#filter-menu"); // 필터 메뉴
  const $filterBtn = $("#filter-btn"); // 필터 열기 버튼
  const $closeBtn = $("#close-btn"); // 닫기 버튼
  const $resetBtn = $("#reset-filter"); // 초기화 버튼
  const $applyBtn = $("#apply-filter"); // 적용 버튼

  // 필터 메뉴 열기
  $filterBtn.on("click", function () {
    // console.log("[Filter] 필터 메뉴 열림");
    $filterMenu.addClass("open");
  });

  // 필터 메뉴 닫기
  $closeBtn.on("click", function () {
    // console.log("[Filter] 필터 메뉴 닫힘");
    $filterMenu.removeClass("open");
  });

  // 필터 초기화 버튼 클릭 이벤트
  $resetBtn.on("click", function () {
    // console.log("[Filter] 필터 초기화 버튼 클릭됨");
    $(".filter-options input").prop("checked", false);
  });

  // 필터 적용 버튼 클릭 이벤트
  $applyBtn.on("click", function () {
    // console.log("[Filter] 필터 적용 버튼 클릭됨");

    const selectedBrands = [];
    const selectedCategories = [];
    let selectedPrice = null;

    // 선택된 브랜드 필터 수집
    $(".filter-options input[name='brand']:checked").each(function () {
      selectedBrands.push($(this).val());
    });

    // 선택된 하위 카테고리 필터 수집
    $(".filter-options input[name='subcategory']:checked").each(function () {
      selectedCategories.push($(this).val());
    });

    // 선택된 가격 필터 수집
    selectedPrice = $(".filter-options input[name='price']:checked").val();

    // 디버깅: 선택된 필터 값 확인
    // console.log("[Filter] 선택된 브랜드:", selectedBrands);
    // console.log("[Filter] 선택된 카테고리:", selectedCategories);
    // console.log("[Filter] 선택된 가격:", selectedPrice);

    // 최소 필터 조건 확인
    if (selectedBrands.length === 0 && selectedCategories.length === 0 && !selectedPrice) {
      alert("적어도 하나의 필터 조건을 선택하세요.");
      // console.warn("[Filter] 필터 조건이 선택되지 않음");
      return;
    }

    const $productList = $("#product-container");
    if ($productList.length === 0) {
      // console.error("[Filter] 상품 리스트 컨테이너가 없음");
      return;
    }

    // 서버로 Ajax 요청
    const filterURL = `${window.location.origin}${window.location.pathname}?page=filter`;
    // console.log("[Filter] Ajax 요청 URL:", filterURL);

    // console.log("[Filter] 서버로 Ajax 요청 시작");
    $.ajax({
      url: filterURL, // 필터 데이터를 처리할 서버 URL
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        brands: selectedBrands, // 브랜드 목록
        categories: selectedCategories, // 하위 카테고리 목록
        price: selectedPrice, // 선택된 가격
      }),
      beforeSend: function () {
        // console.log("[Filter] Ajax 요청 전: 로딩 메시지 표시");
        $productList.html('<p class="loading">로딩 중...</p>');
      },
      success: function (response) {
        // console.log("[Filter] Ajax 성공: 응답 데이터", response);
        $productList.empty(); // 기존 상품 리스트 초기화

        if (response.length === 0) {
          // console.warn("[Filter] 조건에 맞는 상품이 없음");
          $productList.html('<div class="no-products">조건에 맞는 상품이 없습니다.</div>');
        } else {
          // console.log("[Filter] 필터링된 상품 렌더링 시작");
          response.forEach((product) => {
            const productHtml = `
              <a href="${window.location.origin}/haribo/jelly?page=productDetail&productId=${product.productId}" class="product-card">
                <div class="image-wrapper">
                  <img src="${product.imageUrl}" alt="${product.name}">
                </div>
                <div class="brand">${product.brand}</div>
                <div class="product-name">${product.productName}</div>
                <div class="price">${product.initialPrice.toLocaleString()}원</div>
              </a>
            `;
            $productList.append(productHtml);
          });
        }
      },
      error: function (xhr, status, error) {
        console.error("[Filter] Ajax 실패: ", xhr.responseText || error);
        alert("상품데이터 불러오기 실패");
      },
      complete: function () {
        // console.log("[Filter] Ajax 요청 완료");
        $filterMenu.removeClass("open");
        // console.log("[Filter] 필터 메뉴 닫힘");
      },
    });
  });
});