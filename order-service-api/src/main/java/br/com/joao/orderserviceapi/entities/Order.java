package br.com.joao.orderserviceapi.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "tb_order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String requesterId;

    @Column(nullable = false, length = 45)
    private String customerId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime closedAt;
}
