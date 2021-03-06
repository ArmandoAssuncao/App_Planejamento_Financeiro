package gui.framePrincipal;

import gui.painelDespesas.IgPainelDespesas;
import gui.painelGraficos.PainelGraficos;
import gui.painelInicio.PainelInicio;
import gui.painelRenda.IgPainelRenda;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Define as abas principais do programa.
 *  
 * @author Armando Assunção
 * @author Richardson William
 *
 */
public class AbasPrincipal extends JTabbedPane{
	private static final long serialVersionUID = -1454166607272187474L;
	
	private final int TAM_PAINEL_X = 990;
	private final int TAM_PAINEL_Y = 645;
	
	//private final String NOME_ABA_1 = "<html>&nbsp;I<br>N<br>&nbsp;Í<br>C<br>&nbsp;I<br>O";
	//private final String NOME_ABA_1 = "<html>C<br>O<br>M<br>E<br>Ç<br>O";
	private final String NOME_ABA_2 = "<html>R<br>E<br>N<br>D<br>A";
	private final String NOME_ABA_3 = "<html>D<br>E<br>S<br>P<br>E<br>S<br>A<br>S"; 
	private final String NOME_ABA_4 = "<html>R<br>E<br>L<br>A<br>T<br>Ó<br>R<br>&nbsp;I<br>O"; 
	
	PainelInicio painelInicio;
	IgPainelDespesas painelDespesas;
	PainelGraficos painelGraficos;
    IgPainelRenda igPainelRenda;
	
    /**
     * Cria uma instância do painel de abas.
     * @param framePrincipal componente pai
     */
	public AbasPrincipal(GuiPrincipal framePrincipal){
		painelInicio = new PainelInicio(framePrincipal);
		painelDespesas = new IgPainelDespesas(framePrincipal);
		painelGraficos = new PainelGraficos(framePrincipal);
        igPainelRenda = new IgPainelRenda(framePrincipal);
        
		Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		setFont(fonte);
        
		//add(NOME_ABA_1, painelInicio);
		add(NOME_ABA_2, igPainelRenda);
		add(NOME_ABA_3, painelDespesas);
		add(NOME_ABA_4, painelGraficos);
		
		//Atualiza componente da aba selecionada
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if(getSelectedIndex() == 2){
					painelGraficos.atualizaComponentes();
					painelGraficos.getPainelTitulo().limpaPainelTitulo();
				}
				
			}
		});
		
		setTabPlacement(JTabbedPane.LEFT);
		setPreferredSize(new Dimension(TAM_PAINEL_X, TAM_PAINEL_Y));
		setVisible(true);
	}
}
