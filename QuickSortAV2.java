import java.util.Random;

public class QuickSortAV2 {
    private static final Random rand = new Random();

    public static void main(String[] args) {
        int n = 10;

        // ===== EXECUÇÃO DO QUICKSORT ALEATÓRIO =====
        int[] vetorAleatorio = gerarVetor(n);
        quickSortAleatorio(vetorAleatorio, 0, n - 1);
        System.out.println("Aleatório: "
                + java.util.Arrays.toString(vetorAleatorio));

        // ===== EXECUÇÃO DO QUICKSORT PROPORCIONAL =====
        int[] vetorProporcional = gerarVetor(n);
        quickSortProporcional(vetorProporcional, 0, n - 1, 0.25);
        System.out.println("Proporcional: "
                + java.util.Arrays.toString(vetorProporcional));
    }

    // ===== MÉTODOS COMPARTILHADOS =====

    // Gera uma permutação dos números de 1 até n.
    public static int[] gerarVetor(int n) {
        int[] v = new int[n];

        for (int i = 0; i < n; i++) {
            v[i] = i + 1;
        }

        // Embaralha sem repetir valores.
        for (int i = n - 1; i > 0; i--) {
            trocar(v, i, rand.nextInt(i + 1));
        }

        return v;
    }

    // Particionamento de Lomuto, escolhido pela simplicidade.
    private static int lomuto(int[] v, int ini, int fim, int indicePivo) {
        trocar(v, indicePivo, fim);
        int pivo = v[fim];
        int i = ini;

        for (int j = ini; j < fim; j++) {
            if (v[j] < pivo) {
                trocar(v, i, j);
                i++;
            }
        }

        trocar(v, i, fim);
        return i;
    }

    // Troca dois elementos do vetor.
    private static void trocar(int[] v, int i, int j) {
        int temporario = v[i];
        v[i] = v[j];
        v[j] = temporario;
    }

    // ===== QUICKSORT COM PIVÔ ALEATÓRIO =====

    public static void quickSortAleatorio(int[] v, int ini, int fim) {
        while (ini < fim) {
            // Sorteia uma posição para o pivô.
            int p = ini + rand.nextInt(fim - ini + 1);
            int q = lomuto(v, ini, fim, p);

            // Recursa na parte menor e itera na parte maior.
            if (q - ini < fim - q) {
                quickSortAleatorio(v, ini, q - 1);
                ini = q + 1;
            } else {
                quickSortAleatorio(v, q + 1, fim);
                fim = q - 1;
            }
        }
    }

    // ===== QUICKSORT PROPORCIONAL =====

    public static void quickSortProporcional(
            int[] v, int ini, int fim, double alfa) {
        while (ini < fim) {
            // Calcula onde o pivô deve ficar para a proporção desejada.
            int k = ini + (int) Math.round(alfa * (fim - ini));

            // Encontra o elemento que deve ocupar a posição k.
            selecionar(v, ini, fim, k);

            // Faz Lomuto usando esse elemento como pivô.
            int q = lomuto(v, ini, fim, k);

            // Recursa na parte menor e itera na parte maior.
            if (q - ini < fim - q) {
                quickSortProporcional(v, ini, q - 1, alfa);
                ini = q + 1;
            } else {
                quickSortProporcional(v, q + 1, fim, alfa);
                fim = q - 1;
            }
        }
    }

    // Seleciona o elemento que deve ocupar o índice k.
    private static void selecionar(int[] v, int ini, int fim, int k) {
        while (ini < fim) {
            int p = ini + rand.nextInt(fim - ini + 1);
            int q = lomuto(v, ini, fim, p);

            if (q == k) {
                return;
            } else if (k < q) {
                fim = q - 1;
            } else {
                ini = q + 1;
            }
        }
    }
}