package br.com.equipe4.app_produtos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "inventories")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "min_level", nullable = false)
    private Integer minLevel = 5; // padrão que pode ser alterado

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Products product;

    public boolean isLowStock() {
        return this.quantity <= this.minLevel;
    }

}
