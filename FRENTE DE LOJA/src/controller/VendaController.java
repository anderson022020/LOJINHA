package controller;

import model.ItemVenda;
import model.Produto;
import model.Venda;
import service.Caixa;

public class VendaController {

    private Venda venda;
    private Caixa caixa;

    public VendaController() {
        venda = new Venda();
        caixa = new Caixa();
    }

    public Venda getVenda() {
        return venda;
    }

    public void novaVenda() {
        venda = new Venda();
    }

    public boolean adicionarItem(Produto produto, int quantidade) {

        if (produto == null)
            return false;

        if (quantidade > produto.getEstoque())
            return false;

        venda.adicionarItem(new ItemVenda(produto, quantidade));

        return true;
    }

    public double calcularTotal() {
        return venda.calcularTotal();
    }

    public double finalizarVenda(double valorPago) {

        double total = venda.calcularTotal();

        for (ItemVenda item : venda.getItens()) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }

        return caixa.calcularTroco(valorPago, total);
    }
}