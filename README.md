## client

1.뷰페이저에서 프래그먼트 교체할떄 replace적용 안됨(프래그먼트 중복 이슈 발생)
2.FindIdStep2Fragment에서 staractivity 적용 안됨.
<0801 issue solution> fragment안에 fragment 생성해서 해결 - childFragmentManager, parentFragmentManager를 사용
