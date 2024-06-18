package pizzaria.infra;

public abstract class Utils {
	
	public static final String PATTERN_NUMERICO = "^\\d+(.\\d{1,2})?$";
	
	public static String[] unificarArrays(String[] primeiro, String[] segundo) {
		int tamanho = primeiro.length + segundo.length;
		String[] novoArray = new String[tamanho];
		System.arraycopy(primeiro, 0, novoArray, 0, primeiro.length);
		System.arraycopy(segundo, 0, novoArray, primeiro.length, segundo.length);
		return novoArray;
	}
	
	public static Long gerarId(int size) {
		return Long.valueOf(size+1);
	}

}
