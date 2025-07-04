$(document).ready(function () {
        // 페이지 로딩 시 myDiv 표시
        $("#myDiv").show();

        // DataTables 초기화
        $('#myTable').DataTable({
            processing: true,
            serverSide: true,
            searching: true,
            search : { return : true },
            scrollCollapse: true,
            scrollX: true,
            scrollY: '700px',
            fixedColumns: {
                leftColumns: 0,
                rightColumns: 0
            },
            dom: 'Bfrtip',
            lengthMenu: [
                [5, 10, 25, 50, 100, -1],
                ['5 rows', '10 rows', '25 rows', '50 rows', '100 rows', 'Show all']
            ],
            iDisplayLength: 10,
            buttons: ['pageLength', 'copy', 'csv', 'excel', 'pdf', 'print'],
            ajax: {
                url: '/api/historySearch',
                type: 'POST',

                contentType: 'application/json',
                data: function (d) {
                    return JSON.stringify({
                        draw: d.draw,
                        start: d.start,
                        length: d.length,
                        search: d.search.value,
                        orderColumn: d.order[0]?.column,
                        orderDir: d.order[0]?.dir,
                        navSearch: $('#navSearchHistory').val(),
                        navSearchHistory: $('#navSearchHistory').val(),
                        searchType: $('#searchType').val(),
                        viewType: $('#viewType').val(),
                        tableName: $('#tableName').val(),
                        searchStart: $('#searchStart').val(),
                        searchEnd: $('#searchEnd').val()
                    });
                },

                dataSrc: function (json) {
                    const navSearch = json.navSearch || '';
                    const navSearchHistory = json.navSearchHistory || '';
                    const search = json.search || '';
                    const viewType = json.viewType || '';
                    const searchStart = json.searchStart || '';
                    const searchEnd = json.searchEnd || '';

                    // viewType 한글 매핑
                    const viewTypeMap = {
                        'Input': '입고 내역',
                        'Output': '출고 내역',
                        'Repair': '고장 및 수리',
                        'Asset': '장비 내역',
                        'Change': '변경 내역',
                        'Consumables': '소모품 내역'
                    };
                    const viewTypeKor = viewTypeMap[viewType] || viewType;

                    // navSearch가 없으면 "전체 검색"으로 표시
                    const navText = navSearchHistory
                        ? `이력 조회 : <strong>${navSearchHistory}</strong>`
                        : '검색 대상 : <strong>전체</strong>';

                    const viewText = viewTypeKor
                        ? `TYPE : <strong>${viewTypeKor}</strong>`
                        : 'TYPE : <strong>이력 조회</strong>';

                    const searchText = search
                        ? `Search : <strong>${search}</strong>`
                        : 'Search : ';

                    // 날짜 조건별 텍스트 처리
                    let startText = '';
                    let endText = '';

                    if (!searchStart && !searchEnd) {
                        startText = '검색 시작일 : <strong>2017-07-01</strong>';
                        endText = '검색 종료일 : <strong>Today</strong>';
                    } else if (!searchStart && searchEnd) {
                        startText = `검색 시작일 : <strong>${searchEnd}</strong>`;
                        endText = `검색 종료일 : <strong>${searchEnd}</strong>`;
                    } else if (searchStart && !searchEnd) {
                        startText = `검색 시작일 : <strong>${searchStart}</strong>`;
                        endText = '검색 종료일 : <strong>Today</strong>';
                    } else {
                        startText = `검색 시작일 : <strong>${searchStart}</strong>`;
                        endText = `검색 종료일 : <strong>${searchEnd}</strong>`;
                    }

                    // 텍스트 영역에 표시
                    $('#navSearchText').html(navText);
                    $('#viewTypeText').html(viewText);
                    $('#searchText').html(searchText);
                    $('#startText').html(startText);
                    $('#endText').html(endText);

                    // 테이블 데이터 반환
                    return json.data;
                }


            },
            columnDefs: [
                {
                    targets: [0, 13, 14], // 기타 및 스펙 컬럼 정렬 비활성화
                    orderable: false
                }
            ],
            order: [], // 기본 정렬 제거 → 서버에서 설정한 정렬 사용
            colReorder: true,
            columns: [
                { data: 0 },  // No.
                { data: 1 },  // 처리 일시
                { data: 2 },  // 요청 일시
                { data: 3 },  // 구분
                { data: 4 },  // 입출고
                { data: 5 },  // 요청자
                { data: 6 },  // 작업자
                { data: 7 },  // 사번
                { data: 8 },  // 부서
                { data: 9 },  // 이름
                { data: 10 }, // 직급
                { data: 11 }, // 장비번호
                { data: 12 }, // 모델명
                { data: 13 }, // 요청 내용
                { data: 14 } // 비고 내용
                // { data: 15 }, // 스펙1
                // { data: 16 }, // 스펙2
                // { data: 17 }, // 스펙3
                // { data: 18 } // 기타
            ],
            responsive: false
        });
    });