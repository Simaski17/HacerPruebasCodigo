package com.rinno.simaski.hacerpruebascodigo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int[][] m = new int[6][6];
    int[][] shortpath;
    int[][] path = new int[6][6];
    String partes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m[0][0] = 0;
        m[0][1] = 3;
        m[0][2] = 5;
        m[0][3] = 9;
        m[0][4] = 10000;
        m[0][5] = 10000;
        m[1][0] = 3;
        m[1][1] = 0;
        m[1][2] = 3;
        m[1][3] = 4;
        m[1][4] = 7;
        m[1][5] = 10000;
        m[2][0] = 5;
        m[2][1] = 3;
        m[2][2] = 0;
        m[2][3] = 2;
        m[2][4] = 6;
        m[2][5] = 8;
        m[3][0] = 9;
        m[3][1] = 4;
        m[3][2] = 2;
        m[3][3] = 0;
        m[3][4] = 2;
        m[3][5] = 2;
        m[4][0] = 10000;
        m[4][1] = 7;
        m[4][2] = 6;
        m[4][3] = 2;
        m[4][4] = 0;
        m[4][5] = 5;
        m[5][0] = 10000;
        m[5][1] = 10000;
        m[5][2] = 8;
        m[5][3] = 2;
        m[5][4] = 5;
        m[5][5] = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (m[i][j] == 10000) {
                    path[i][j] = -1;
                } else {
                    path[i][j] = i;
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            path[i][i] = i;
        }

        shortpath = shortestpath(m, path);

        int start = 0;
        int end = 4;

        String myPath = end + "";

        while (path[start][end] != start) {
            myPath = path[start][end] + "->" + myPath;
            end = path[start][end];
        }

        Log.e("TAG","PATH: "+myPath);

        myPath = start + "->" + myPath;
        Log.e("TAG","ESTE ES EL CAMINO: "+myPath);

        partes = myPath;

        Intent i = new Intent(this, DrawLineActivity.class);
        i.putExtra("partes", partes);
        startActivity(i);
        Log.e("TAG", "PARTES PARTES : " + partes);

    }

    public static int[][] shortestpath(int[][] adj, int[][] path) {

        int n = adj.length;
        int[][] ans = new int[n][n];

        // Implementar el algoritmo en una matriz de copia de modo que la adyacencia no es
        //destruido.
        copy(ans, adj);

        // Calcular rutas sucesivamente a través de una mejor k vértices.
        for (int k = 0; k < n; k++) {

            // Es así que entre cada par de puntos posibles.
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {


                    if (ans[i][k] + ans[k][j] < ans[i][j]) {
                        ans[i][j] = ans[i][k] + ans[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }

        // Devuelva la matriz camino más corto.
        return ans;
    }

    public static void copy(int[][] a, int[][] b) {

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
    }
}



