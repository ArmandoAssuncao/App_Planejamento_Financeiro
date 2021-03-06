package funcoes;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Classe para realizar operações de conversão de dados.
 * 
 * @author Armando Assunção
 * @author Richardson William
 *
 */
public final class Converte {
	/**
	 * Retorna uma representação em <code>String</code> da data.
	 * @param data objeto <code>Calendar</code> com uma data.
	 * @return <code>String</code> no formato DD/MM/AAAA representando a data.
	 */
	public static String calendarToString(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data.getTime());
	}
	
	/**
	 * Retorna uma representação em <code>Calendar</code> da data em <code>String</code>.
	 * @param string objeto <code>String</code> com uma data o formato DD/MM/AAAA
	 * @return <code>Calendar</code> obtida do parâmetro data.
	 */
	public static Calendar stringToCalendar(String string) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		try {
			c.setTime(sdf.parse(string));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	/**
	 * Dado um valor <code>double</code>, este método substitui o separador de "," (vírgula) por "." (ponto).
	 * @param valor valor a ser manipulado.
	 * @return valor modificado.
	 */
	public static Double double2Decimais(Double valor){
		DecimalFormat df = new DecimalFormat("#.##");
		
		return Double.valueOf( df.format(valor).replace(",", ".") );
		
	}
}
