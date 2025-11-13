package com.sistemapapelaria.api.repository;

import com.sistemapapelaria.api.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findByPedidoId(Integer pedidoId);

    default double somarTotalPorPedido(Integer pedidoId) {
        return findByPedidoId(pedidoId).stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }
}
