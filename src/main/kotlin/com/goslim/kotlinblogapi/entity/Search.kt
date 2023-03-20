package com.goslim.kotlinblogapi.entity

import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Search(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "count", columnDefinition = "bigint not null")
    @Comment("검색 횟수")
    var count: Long = 0,

    @Column(name = "keyword", columnDefinition = "text not null")
    @Comment("계약 고객 휴대폰 번호")
    var keyword: String
) {
    override fun toString(): String {
        return "Search(id=$id, count=$count, keyword='$keyword')"
    }
}