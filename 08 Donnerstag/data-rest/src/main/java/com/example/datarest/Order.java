package com.example.datarest;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DynamicUpdate
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_order_seq")
    @SequenceGenerator(name = "gen_order_seq",
            sequenceName = "SEQ_ORDERS_ID", allocationSize = 1)
    private Long id;

    @Version
    private int version;

    @NotNull
    @Length(max = 255)
    private String orderNumber;

    @NotNull
    @Length(max = 255)
    private String notes;
}
