package org.myboosttest.purchaseorders.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 500)
    private String name;
    @Column(length = 500)
    private String description;
    private Integer price;
    private Integer cost;
    private String createdBy;
    private String updatedBy;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDatetime;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedDatetime;
}
