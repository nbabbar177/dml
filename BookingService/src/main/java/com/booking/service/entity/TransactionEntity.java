package com.booking.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Subselect;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @Column(name = "transaction_id", nullable = false)
    private int transaction_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "currency")
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransactionEntity parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<TransactionEntity> children;
}
