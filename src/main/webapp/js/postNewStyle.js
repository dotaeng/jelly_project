$(document).ready(function() {
	// 상품 태그 모달 열기
   $('#tagBtn').on('click', function () {
		$('.modal-overlay').css('display', 'flex');
		$('.search-input').focus();
   });

    // 모달 닫기
    $('.modal-close').on('click', function () {
        $('.modal-overlay').css('display', 'none');
    });

    // 모달 외부 클릭 시 닫기
    $(document).on('click', function (e) {
        if ($(e.target).hasClass('modal-overlay')) {
            $('.modal-overlay').css('display', 'none');
        }
    });
	
	$('.search-form').on('submit', function (event) {
	    event.preventDefault(); // 폼 기본 동작 방지

		const contextPath = $(this).data("context-path");
	    const query = $('.search-input').val().trim();
		
	    if (query === "") {
	        alert("검색어를 입력해주세요.");
	        return;
	    }

	    // AJAX 요청 보내기
	    $.ajax({
	        url: contextPath + '/jelly?page=search&query=' + query, 
			type: "GET",
			dataType: 'html',
			success: function (html) {
                // 모달 콘텐츠에 팔로워 목록 추가
                $('#product-results').html(html);
            },
            error: function (xhr, status, error) {
                console.error("Ajax 오류:", status, error);
                alert("서버 요청 중 오류가 발생했습니다.");
            }
	    });
	});
	
	var selectedProducts = [];  // 선택된 상품 목록

    // 상품 클릭 시 처리
    $(document).on('click', '.product-card', function() {
        var productCard = $(this);
        var product = {
			productId: productCard.find('img').attr('alt'),
            imageUrl: productCard.find('img').attr('src')
		};
		// 중복 체크
		if (!selectedProducts.some(item => item.productId === product.productId)){
	        if (selectedProducts.length < 5) {
				selectedProducts.push(product); // 선택된 상품 목록에 추가
			    const $img = $(`<img src="${product.imageUrl}" alt="${product.productId}">`);
			    $img.on('click', function() {
			        $(this).remove(); // 클릭된 이미지 제거
					selectedProducts = selectedProducts.filter(item => item !== product);
			    });
			    $('#selected-product').append($img);
	        }
		} else {
	        alert('이미 선택된 상품입니다.'); // 중복 경고 메시지
	    }	
    });

    // "추가" 버튼 클릭 시 form에 이미지 추가
    $('#add-products-btn').on('click', function() {
		$('#tag-products').empty();
		for (const product of selectedProducts) {
	        $('#tag-products').append(`<img src="${product.imageUrl}" alt="${product.productId}">`);
	    }
		$('.modal-overlay').css('display', 'none');
		
		// 선택된 productId를 콤마로 구분된 문자열로 변환
	    const productIds = selectedProducts.map(product => product.productId).join(',');
	    // 숨겨진 필드에 값 설정
	    $('#product-ids').val(productIds);
    });
	
	// 검색시 x버튼 클릭 시 검색결과 초기화
	$('.search-reset').on('click', function() {
	    $('#product-results').empty();
	});
})