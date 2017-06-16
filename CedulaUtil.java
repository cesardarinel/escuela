package com.cesardarinel.escuela;

//import static com.acap.bizagiservicios.utilitario.StringUtils.rellenaDelante;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Clase para manipular y confirmar que una cedula sea real.
 *
 * @author acap1505
 */
public class CedulaUtil {

    private static StringTokenizer separador;
    public static final String SEPARADORPORDEFECTO = "-";
    private static CedulaUtil manager = null;
    public static final Pattern CEDULA_REGEX = Pattern.compile("[0-9]{3}-[0-9]{7}-[0-9]");
    public static final Pattern ALL_BUT_DIGITS = Pattern.compile("\\D");

    /**
     * En base a una cedula en cualquier formato de string, retorna una cedula
     * completada con 0 a 11 caracteres mas guiones.
     * formato de cedula ###-#######-#
     *
     * @param cedula
     * @return
     */
    public static String cedulaGuiones(String cedula) {
        cedula = cedulaSinGuiones(cedula);
        cedula = cedula.substring(0, 3) + "-" + cedula.substring(3, 10) + "-" + cedula.substring(10, 11);
        return cedula;
    }
    
    /**
     * En base a una cedula en cualquier formato de string, retorna una cedula
     * completada con 0 a 11 caracteres.
     * formato de cedula ###########
     * 
     * @param cedula
     * @return 
     */
    public static String cedulaSinGuiones(String cedula){
        cedula = Strings.padStart(ALL_BUT_DIGITS.matcher(cedula).replaceAll(""), 11, '0');
        return cedula;
    }

    /**
     * Valida una cedula utilizando modulo 10
     *
     * @param cedula
     * @return
     */
    static public boolean validarCedula(String cedula) {
        return validarCedula(cedulaSinGuiones(cedula).toCharArray());
    }

    /**
     * Valida una cedula utilizando modulo 10
     *
     * @param cedula
     * @return true si la cedula es valida
     */
    static private boolean validarCedula(char[] cedula) {
        int suma = 0;
        int division = 0;
        final char[] peso = {'1', '2', '1', '2', '1', '2', '1', '2', '1', '2'};
        if (cedula.length != 11) {
            return false;
        } else {
            for (int i = 0; i < 10; i++) {
                int a = Character.getNumericValue(cedula[i]);
                int b = Character.getNumericValue(peso[i]);
                char[] mult = Integer.toString(a * b).toCharArray();
                if (!Character.isDigit(cedula[i])) {
                    return false;
                }
                if (mult.length > 1) {
                    a = Character.getNumericValue(mult[0]);
                    b = Character.getNumericValue(mult[1]);
                } else {
                    a = 0;
                    b = Character.getNumericValue(mult[0]);
                }
                suma = suma + a + b;
            }
            division = (suma / 10) * 10;
            if (division < suma) {
                division += 10;
            }
            int digito = division - suma;
            if (digito != Character.getNumericValue(cedula[10])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo para verificar una cedula por el modulo 10. Se utilizan los
     * primeros 10 digitos para verificar el ultimo.
     *
     * @param cedula
     * @return valida
     * @depracated
     */
    public static Boolean modulo10(String cedula) {
        Boolean valida = false;
        ArrayList<Integer> digitos = arregloConEnteros(cedula);

        if (digitos != null) {
            Double acumulado = 0.0;
            for (int i = 0; i < digitos.size() - 1; i++) {

                if (i % 2 != 0) {
                    if ((digitos.get(i) * 2) > 10) {
                        acumulado += (double) ((digitos.get(i) * 2) / 10) + ((digitos.get(i) * 2) % 10);
                    } else {
                        acumulado += digitos.get(i) * 2;
                    }
                } else {
                    acumulado += digitos.get(i);
                }
            }

            if (acumulado % 10 != 0) {
                if ((10 - acumulado % 10) == digitos.get(digitos.size() - 1)) {
                    valida = true;
                }
            } else if ((acumulado % 10) == digitos.get(digitos.size() - 1)) {
                valida = true;
            }

        }

        return valida;
    }

    /**
     * Verifica que la cadena contenga la estructura adecuada.
     *
     * @param cedula
     * @return
     */
    public static Boolean isCedula(String cedula) {
        return CEDULA_REGEX.matcher(cedula).matches();
    }

    /**
     * Metodo que devuelve un texto sin el SEPARADORPORDEFECTO de la clase.
     *
     * @param cedula
     * @return
     */
    public static String cedulaSinGuines(String cedula) {
        String cedulasinguinoes = "";
        separador = new StringTokenizer(cedula, SEPARADORPORDEFECTO);

        while (separador.hasMoreTokens()) {
            cedulasinguinoes += separador.nextToken();
        }

        return cedulasinguinoes;

    }

    /**
     * Quita todas las delimitaciones de un texto y retorna el texto sin ellas.
     *
     * @param cedula
     * @param delimitador
     * @return
     */
    public static String cedulaSinGuines(String cedula, String delimitador) {
        String cedulasinguinoes = "";
        separador = new StringTokenizer(cedula, delimitador);

        while (separador.hasMoreTokens()) {
            cedulasinguinoes += separador.nextToken();
        }

        return cedulasinguinoes;

    }

    /**
     * Coloca el delimitador deseado en la ubicacion que se supone debe ir en la
     * cedula.
     *
     * @param cedula
     * @return
     */
    public static String cedulaColocar(String cedula, String delimitador) {
        return cedula.substring(0, 3) + delimitador + cedula.substring(3, 10) + delimitador + cedula.substring(10, 11);
    }

    /**
     * Coloca el separador por defecto en la ubicacion que se supone debe ir en
     * la cedula.
     *
     * @param cedula
     * @return
     */
    public static String cedulaColocar(String cedula) {
        return cedula.substring(0, 3) + SEPARADORPORDEFECTO + cedula.substring(3, 10) + SEPARADORPORDEFECTO + cedula.substring(10, 11);
    }

    /**
     * Retorna todos los numeros de la cedula en forma de arreglo.
     *
     * @param cedula
     * @return
     */
    public static ArrayList<Integer> arregloConEnteros(String cedula) {
        ArrayList<Integer> digitos = new ArrayList<Integer>();
        String singuion = cedulaSinGuines(cedula);

        if (!isCedula(cedula)) {
            return null;
        }

        for (char caracter : singuion.toCharArray()) {
            digitos.add(Integer.parseInt(caracter + ""));
        }

        return digitos;
    }

    /**
     * Retorna todos los numeros de la cedula en forma de arreglo.
     *
     * @param cedula
     * @return
     */
    public static Long cedulaEnNumero(String cedula) {
        String singuion = cedulaSinGuines(cedula);

        if (!isCedula(cedula)) {
            return null;
        }

        return new Long(singuion);
    }
}
