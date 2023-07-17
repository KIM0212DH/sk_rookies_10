package SuperStrong.coinWallet.repository;

import SuperStrong.coinWallet.entity.MemberTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberTmpRepository extends JpaRepository<MemberTmp, String> {
    MemberTmp findByEmail(String email);
    MemberTmp findByMemberId(String memberId);

    Optional<MemberTmp> findByCode(String code);

    Boolean existsByCode(String code);
}
