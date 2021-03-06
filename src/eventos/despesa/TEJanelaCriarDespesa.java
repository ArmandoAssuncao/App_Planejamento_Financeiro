package eventos.despesa;

import gui.despesa.JanelaCriarDespesa;
import gui.painelDespesas.IgPainelDespesas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

import persistencia.CategoriaDAO;
import persistencia.DespesaDAO;
import persistencia.FormaPagamentoDAO;
import persistencia.PlanejamentoMensalDAO;
import classes.Despesa;

/**
 * Classe para tratar os eventos da janela Criar Despesa.
 * 
 * @author Armando Assunção
 * @author Richardson William
 *
 *@see ActionListener
 */
public class TEJanelaCriarDespesa implements ActionListener{
	private JanelaCriarDespesa janelaCriarDespesa;
	private IgPainelDespesas igPainelDespesas;
	
	/**
	 * Cria uma instância do tratador de eventos da janela.
	 * 
	 * @param janelaCriarDespesa janela de onde vem os eventos.
	 * @param igPainelDespesas painel interno da janela //TODO está certo?
	 */
	public TEJanelaCriarDespesa(JanelaCriarDespesa janelaCriarDespesa, IgPainelDespesas igPainelDespesas) {
		this.janelaCriarDespesa = janelaCriarDespesa;
		this.igPainelDespesas = igPainelDespesas;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == janelaCriarDespesa.getBotaoCriar()){
			if(janelaCriarDespesa.validaCampos()){
				Despesa despesa = janelaCriarDespesa.retornaDespesa();
				
				String descricaoCategoria;
				String descricaoPagamento;
				try {
					descricaoCategoria = new CategoriaDAO().getDescricao(despesa.getIdCategoria());
					descricaoPagamento = new FormaPagamentoDAO().getDescricao(despesa.getIdFormaPagamento());
				} catch (SQLException e) {
					e.printStackTrace();
					return;
				}
				
				DespesaDAO despesaDAO = new DespesaDAO();
				PlanejamentoMensalDAO planejamentoMensalDAO = new PlanejamentoMensalDAO();
				
				despesaDAO.inserir(despesa, descricaoCategoria, descricaoPagamento);
				//não usa o primeiro arg
				planejamentoMensalDAO.inserir(1, despesa.getDataDespesa());
				
				Calendar dataAtual = Calendar.getInstance();
				
				if(dataAtual.get(Calendar.MONTH) == despesa.getDataDespesa().get(Calendar.MONTH) && dataAtual.get(Calendar.YEAR) == despesa.getDataDespesa().get(Calendar.YEAR))
					igPainelDespesas.criarDespesa(despesa);
				
				janelaCriarDespesa.finalizaJanelaDespesa();
			}
		}
		else if(event.getSource() == janelaCriarDespesa.getBotaoCancelar()){
			janelaCriarDespesa.finalizaJanelaDespesa();
		}
	}
}
