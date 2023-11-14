import java.util.Scanner;

public class StringCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите арифметическое выражение: ");
            String input = scanner.nextLine();

            int result = calculateExpression(input);
            System.out.println("Результат: " + result);

        }
        catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
        finally {
            scanner.close();
        }
    }

    // Вычисление значения выражения
    private static int calculateExpression(String expression) {
        // Определение знака первого числа
        boolean isFirstNegative = expression.charAt(0) == '-' && Character.isDigit(expression.charAt(1));
        if (isFirstNegative)
            expression = expression.substring(1);

        // Разбиение строки на токены
        expression = expression.replaceAll(" ", "");
        String[] tokens = expression.split("[+\\-*/]");

        // Проверка на количество токенов
        if (tokens.length > 3 || tokens.length < 2)
            throw new IllegalArgumentException("Неправильный формат выражения.");

        // Проверка токенов на правильный формат
        for (int i = 0; i < tokens.length; ++i)
            if (tokens[i].length() == 0)
                throw new IllegalArgumentException("Неправильный формат выражения.");

        // Извлечение чисел и операций из токенов
        int a = getValidNumber(tokens[0]);
        if (isFirstNegative) {
            a = -a;
        }
        char operation1 = expression.charAt(tokens[0].length());
        int b = getValidNumber(tokens[1]);

        char operation2 = '*';
        int c = 1;
        if (tokens.length == 3) {
            operation2 = expression.charAt(tokens[0].length() + tokens[1].length() + 1);
            c = getValidNumber(tokens[2]);
        }

        // Проверка на поддерживаемую комбинацию операций
        if ((operation1 == '+' || operation1 == '-' || operation1 == '/' || operation1 == '*')
                && (operation2 == '+' || operation2 == '-' || operation2 == '/' || operation2 == '*'))
            return calculate(a, b, c, operation1, operation2);
        else
            throw new IllegalArgumentException("Неподдерживаемая комбинация операций.");
    }

    // Извлечение корректного числа из токена
    private static int getValidNumber(String token) {
        int number = 0;
        try {
            number = Integer.parseInt(token);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Число должно быть целым.");
        }
        if (number < 1 || number > 10)
            throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно.");
        return number;
    }

    // Вычисление результата
    private static int calculate(int a, int b, int c, char operation1, char operation2) {
        if (operation1 == '+' && operation2 == '+')
            return a + b + c;
        if (operation1 == '+' && operation2 == '-')
            return a + b - c;
        if (operation1 == '+' && operation2 == '*')
            return a + b * c;
        if (operation1 == '+' && operation2 == '/')
            return a + b / c;

        if (operation1 == '-' && operation2 == '+')
            return a - b + c;
        if (operation1 == '-' && operation2 == '-')
            return a - b - c;
        if (operation1 == '-' && operation2 == '*')
            return a - b * c;
        if (operation1 == '-' && operation2 == '/')
            return a - b / c;

        if (operation1 == '*' && operation2 == '+')
            return a * b + c;
        if (operation1 == '*' && operation2 == '-')
            return a * b - c;
        if (operation1 == '*' && operation2 == '*')
            return a * b * c;
        if (operation1 == '*' && operation2 == '/')
            return a * b / c;

        if (operation1 == '/' && operation2 == '+')
            return a / b + c;
        if (operation1 == '/' && operation2 == '-')
            return a / b - c;
        if (operation1 == '/' && operation2 == '*')
            return a / b * c;
        if (operation1 == '/' && operation2 == '/')
            return a / b / c;

        else
            throw new IllegalArgumentException("Ошибка вычисления.");
    }
}
