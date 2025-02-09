package org.myboosttest.purchaseorders.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "po_d")
public class PODetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "poh_id", nullable = false, referencedColumnName = "poh_id")
    private POHeader poHeader;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id", updatable = false, nullable = false)
    private Item item;

    private Integer itemQty;
    private Integer itemCost;
    private Integer itemPrice;

}
