package eventos.painelRenda;

import gui.painelRenda.PainelRenda;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TEPainelRenda implements ActionListener {
	private PainelRenda painelRenda;
	Window janelaPai;
	
	public TEPainelRenda(PainelRenda painelRenda, Window janelaPai) {
		this.painelRenda = painelRenda;
		this.janelaPai = janelaPai;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == painelRenda.getBotaoAddRenda())
			System.out.println("adicionar");
		else if(e.getSource() == painelRenda.getBotaoExcluirRenda())
			System.out.println("excluir");
		else System.out.println("editar ");
		
	}

}
