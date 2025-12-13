# Controller, Service 내에서 로그인한 토큰 내에 정보 가져오기

- [방법1] 파라미터에 @AuthenticationPrincipal UserDetails 추가
- [방법2] Authentication authentication = SecurityContextHolder.getContext().getAuthentication();으로 직접가져오기

```
@GetMapping("/reservations")
public List<ReservedRoom> getReservedRooms(@AuthenticationPrincipal UserDetails userDetails, @RequestHeader("Authorization") String token) {
        // SecurityContext에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        log.info("authentication >> {}", authentication);
        log.info("userDetails >> {}", userDetails);


        // UserDetails에서 email 가져오기
        String email1 = authentication.getName();
        String email2 = userDetails.getName();
        
        return reservationService.getReservedRooms(email);
}
```