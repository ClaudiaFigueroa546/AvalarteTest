package com.test.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class pageExercise1 {
	
	static By opermat1 = By.xpath("/html/body/div[2]/form/div[1]/div[3]/p[2]");
	static By radiobutton = By.xpath("//input[@type='radio']");	
	static By opermat2 = By.xpath("/html/body/div[2]/form/div[1]/div[4]/p[2]");
	static By dropdownvalues = By.xpath("/html/body/div[2]/form/div[1]/div[4]/select");
	static By stringDaysAndDate = By.xpath("/html/body/div[2]/form/div[1]/div[2]/p");
	static By dateBox = By.xpath("/html/body/div[2]/form/div[1]/div[2]/input");
	static By StringImage = By.xpath("/html/body/div[2]/form/div[1]/div[1]/div/p[1]");
	static By allImageOPtions = By.xpath("/html/body/div[2]/form/div[1]/div[1]/div/p[2]");
	static By imageBox = By.xpath("/html/body/div[2]/form/div[1]/div[1]/input");
	static By SubmitBtn = By.xpath("/html/body/div[2]/form/div[2]/button");
	
	
	
	public void executeOperations(WebDriver driver) throws InterruptedException {
		
		for (int i = 1; i <= 10; i++) {
			System.out.println("CICLO: "+ i );
			OperResultRadioButtons(driver);
			OperResultDropdown(driver);
			calculateDate(driver);
			countingImage(driver);
			WebElement sendBtn = driver.findElement(SubmitBtn);
			sendBtn.click();
			Thread.sleep(2000);		
		}	
		
		
			
			
		}
	
	
	
	
	
	public static String getStringOperacionMath(WebDriver driver, By selector) {

		WebElement firstEval= driver.findElement(selector);
		String gettingString = firstEval.getText();
		
		return gettingString;
	}	
	
	
	
	public static String gettingStringFromDouble(BigDecimal value) {
		
		String result = value.toString();
		if (result.contains(".") && result.matches(".*\\.0+")) {
			result = result.replaceAll("\\.0+$", "");
		}
		return result;
	}
	
	
	public static BigDecimal evaluarExpresion(String expresion) {
        Stack<BigDecimal> numeros = new Stack<BigDecimal>();
        Stack<Character> operadores = new Stack<Character>();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);

            if (caracter == ' ') {
                continue;
            }

            if (Character.isDigit(caracter)) {
                StringBuilder sb = new StringBuilder();
                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    sb.append(expresion.charAt(i));
                    i++;
                }
                BigDecimal numero = new BigDecimal(sb.toString());
                numeros.push(numero);
                i--; // Retroceder el índice para procesar correctamente el siguiente carácter
            } else if (esOperador(caracter)) {
                while (!operadores.isEmpty() && tienePrecedencia(caracter, operadores.peek())) {
                	BigDecimal numero2 = numeros.pop();
                	BigDecimal numero1 = numeros.pop();
                    char operador = operadores.pop();
                    BigDecimal resultado = aplicarOperacion(numero1, operador, numero2);
                    numeros.push(resultado);
                }
                operadores.push(caracter);
            }
        }

        while (!operadores.isEmpty()) {
        	BigDecimal numero2 = numeros.pop();
        	BigDecimal numero1 = numeros.pop();
            char operador = operadores.pop();
            BigDecimal resultado = aplicarOperacion(numero1, operador, numero2);
            numeros.push(resultado);
        }

        return numeros.pop();
    }
	
	public static boolean esOperador(char caracter) {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/';
    }

    public static boolean tienePrecedencia(char operador1, char operador2) {
        if (operador2 == '(' || operador2 == ')')
            return false;
        if ((operador1 == '*' || operador1 == '/') && (operador2 == '+' || operador2 == '-'))
            return false;
        return true;
    }

    public static BigDecimal aplicarOperacion(BigDecimal numero1, char operador, BigDecimal numero2) {
        switch (operador) {
            case '+':
                return numero1.add(numero2);
            case '-':
                return numero1.subtract(numero2);
            case '*':
                return numero1.multiply(numero2);
            case '/':
                return numero1.divide(numero2);
            default:
                return BigDecimal.ZERO;
        }
    }
    
    public static int gettingNumber(String texto) {
       	String patron = "([0-9]+)";
        Pattern pattern = Pattern.compile(patron);

        // Buscar el número en el texto utilizando el patrón
        Matcher matcher = pattern.matcher(texto);
        
        int numero = 0; 
        
        if (matcher.find()) {
            String numeroStr = matcher.group(1);
            numero= Integer.parseInt(numeroStr);
            
        }
        return numero;    	
    	
    }
	
	
	public static void OperResultRadioButtons(WebDriver driver)  {
		
		String firstEvalString = getStringOperacionMath(driver,opermat1);
				
		String expresion = firstEvalString.substring(0, firstEvalString.length() - 2);
		//System.out.println("Expression:"+ expresion);
		
		String separator=expresion;
		String[] values = null;
		
		if (separator.contains("*")) {
			expresion = expresion.replaceAll("\\*", "\\\\*");
			//System.out.println("expresion "+expresion);
			}
		
		//values = expresion.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
		BigDecimal res = evaluarExpresion(expresion);
		System.out.println("resultado operacion matematica 1 "+res);
		String result = gettingStringFromDouble(res);
		
		//System.out.println("string "+result);
		
		List<WebElement> radioButtons = driver.findElements(radiobutton);
		for (WebElement radioButton : radioButtons) {
			String radioValue = radioButton.getAttribute("value");
			//System.out.println(radioValue);
			if (radioValue.equals(result) ) {
				
				radioButton.click();
				break;
			} 
		}				
		//return res;
		}
	
	
	public static void OperResultDropdown(WebDriver driver) {
    	
    	String firstEvalString = getStringOperacionMath(driver,opermat2);
    	String separator=firstEvalString;
		String[] values = null;
		
		if (separator.contains("*")) {
			firstEvalString = firstEvalString.replaceAll("\\*", "\\\\*");
			//System.out.println("expresion "+firstEvalString);
			}
		
		//values = expresion.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
		BigDecimal res = evaluarExpresion(firstEvalString);
		System.out.println("resultado Operacion Matematica 2 "+res);
		String result = gettingStringFromDouble(res);
		
		WebElement dropdownElement = driver.findElement(dropdownvalues);
		
		Select dropdownSelect = new Select(dropdownElement);
		dropdownSelect.selectByValue(result);
		
		    	
    }
	
	public void calculateDate(WebDriver driver) {
		
		String gettingString = getStringOperacionMath(driver,stringDaysAndDate);
		
		String[] partes = gettingString.split(":");
        String firstPart = partes[0].trim();
        int days = gettingNumber(firstPart);
        String OriginalDate = partes[1].trim();
        
        System.out.println("Dias a restar: " + days);
        System.out.println("Fecha Original: " + OriginalDate);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaOriginal = LocalDate.parse(OriginalDate, formatter);
        
        if (gettingString.contains("before")){

        // Restar días a la fecha original
        LocalDate fechaResultado = fechaOriginal.minusDays(days);

        // Obtener la fecha resultante como una cadena
        DateTimeFormatter nuevoFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String fechaResultadoString = fechaResultado.format(nuevoFormatter);
        System.out.println("Fecha resultado: " + fechaResultadoString);
        
        driver.findElement(dateBox).sendKeys(fechaResultadoString);
        }
        else {
        	LocalDate fechaResultado = fechaOriginal.plusDays(days);

            // Obtener la fecha resultante como una cadena
            DateTimeFormatter nuevoFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String fechaResultadoString = fechaResultado.format(nuevoFormatter);
            System.out.println("Fecha resultado: " + fechaResultadoString);
            
            driver.findElement(dateBox).sendKeys(fechaResultadoString);
        }
        	
        		
	}
	
	public void countingImage(WebDriver driver) {
		
		String texto = driver.findElement(StringImage).getText();
		String allImages = driver.findElement(allImageOPtions).getText();
		//System.out.println("El texto: " + texto);
		//System.out.println("El texto: " + allImages);
		
		String[] fisrtText = texto.split("many");
		String parte1 = fisrtText[1].trim();
		String[] secondText = parte1.split("there");
		String icon = secondText[0].trim();
		
		System.out.println("El icon: " + icon);
		
		String encodedIcon = null;
		String encodedAll = null;
        
        try {
        	encodedIcon = URLEncoder.encode(icon, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }        
        //System.out.println("Encode: "+ encodedIcon);
        try {
        	encodedAll = URLEncoder.encode(allImages, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }    
		
        
        int contador = 0;
        int indice = 0;

        while ((indice = encodedAll.indexOf(encodedIcon, indice)) != -1) {
            contador++;
            indice += encodedIcon.length();
        }
        
        System.out.println("El total: " + contador);
        //\ud83d\udc13
        //Encode: %F0%9F%90%93
        String result = String.valueOf(contador); 
        driver.findElement(imageBox).sendKeys(result);
        
        
		
	}
	
	

}
