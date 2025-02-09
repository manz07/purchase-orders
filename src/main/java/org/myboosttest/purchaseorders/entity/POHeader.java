package org.myboosttest.purchaseorders.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "po_h")
@EntityListeners(AuditingEntityListener.class)
public class POHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poh_id")
    private Integer id;

    @CreatedDate
    @Column(updatable = false, nullable = false)

    private LocalDateTime dateTime;

    @Column(length = 500)
    private String description;

    private Integer totalPrice;
    private Integer totalCost;
    private String createdBy;
    private String updatedBy;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDatetime;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedDatetime;

    @OneToMany(mappedBy = "poHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PODetail> poDetails;
}
