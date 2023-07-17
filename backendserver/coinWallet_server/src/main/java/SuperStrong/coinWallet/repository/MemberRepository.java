package SuperStrong.coinWallet.repository;

import SuperStrong.coinWallet.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByEmail(String email);
    Member findByMemberId(String memberId);

    Member findByPubAddress(String pub_address);

    Member findByPrivateKey(String private_key);


    Boolean existsByMemberId(String memberId);
    Boolean existsByPubAddress(String pubAddress);
    Optional<Member> findByWallet_WalletId(String walletId);

    Optional<Member> findByHistories_HistoryId(int historyId);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNum(String phoneNum);


}

