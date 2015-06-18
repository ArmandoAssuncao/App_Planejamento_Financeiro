package classes;
/**
 * Armazena informações sobre a categoria dos gastos.
 * Possui os parâmetros <code>int</code>codigo e <code>String</code> descrição herdados
 * da superclasse abstrata <code>Identificacao</code>.
 * @author Armando e Richardson
 */
public class Categoria extends Identificacao {
	/**
	 * Construtor padrão.
	 */
	public Categoria() {
		super();
	}//Categoria()
	
	/**
	 * Construtor sobrecarregado da classe Categoria. Recebe o seguinte parâmetro:
	 * @param codigo <code>int</code> da categoria.
	 */
	public Categoria(int codigo){
		super(codigo);
	}//Categoria()
	
	/**
	 * Construtor sobrecarregado da classe Categoria. Recebe os seguintes parâmetros:
	 * @param codigo <code>int</code> da categoria.
	 * @param descricao <code>String</code> da categoria.
	 */
	public Categoria(int codigo, String descricao) {
		super(codigo, descricao);
	}//Categoria()
	
	/**
	 * Retorna uma referência em String do código e da descrição da Categoria.
	 * @return <code>String</code> referente aos dados cadastrados.
	 */
	@Override
	public String toString() {
		return "Categoria [ " + super.toString() + " ]";
	}

	public static boolean categoriaExists(int codigoCategoria) {
		//TODO 
		boolean existe = false;
		return existe;
	}

	public static int getId(int codigoCategoria) {
		// TODO Auto-generated method stub
		return 0;
	}
}//class Categoria