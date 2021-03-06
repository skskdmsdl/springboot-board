package com.study.springbootboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount; // 유저 정보 (ID)
    
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    // Entity class 안의 모든 필드는 컬럼으로 간주하기에
    // 옵션설정이 필요하지 않으면 @Column 작성 안해도 됨
    @Setter private String hashtag; // 해시태그

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {}

    // 메타 데이터(자동 생성되는 데이터) 빼고 생성자 열어주기
    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // factory method 로 제공(Article을 new 키워드 없이 사용)
    // 도메인 Article 생성 시 어떤 값을 필요로 하는지 가이드
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        // 새로 만든 entity 가 영속화 되지 않았다면 entity는 동등성 검사를 탈락한다.
        // id 가 부여(영속성 부여)되지 않았으면 동등하지 않다.
        return id != null && id.equals(that.getId());    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
