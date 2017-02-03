package com.rinno.simaski.hacerpruebascodigo;


import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    //custom drawing view
    private DrawingView drawView;
    private DrawingPointView drawViewPoint;

    int[][] m = new int[26][26];
    int[][] path = new int[26][26];
    int[][] shortpath;
    public String partes;


    ImageView prueba;
    RelativeLayout rl;

    Button btPrueba;
    Thread hilo;
    Thread actual;
    int i;
    String idGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btPrueba = (Button) findViewById(R.id.btPrueba);

        /*Runnable hiloFor = new Runnable() {

            public void run() {
                Thread actual = Thread.currentThread();
                synchronized(actual) {
                    for(int i = 0; i < 10; i++){
                        if(i == 4 || i == 6 || i == 8){
                            try {
                                Log.e("TAG","VALOR DE i = "+i);
                                activarBoton();
                                actual.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        hilo = new Thread(hiloFor);
        hilo.start();*/

        //get drawing view
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawViewPoint = (DrawingPointView) findViewById(R.id.drawingPoint);

        /*prueba = (ImageView) findViewById(R.id.prueba);*/
        prueba = (ImageView) findViewById(R.id.prueba);
        prueba.setX(150);
        prueba.setY(600);

        //m= new int[][]{{0, 3, 5, 2, 10000, 10000, 10000, 10}, {3, 0, 5, 8, 4, 10000, 6, 6}, {5, 5, 0, 10000, 1, 7, 9, 10000}, {2, 6, 10000, 0, 8, 10000, 10000, 14}, {10000, 4, 1, 8, 0, 10000, 15, 10000}, {10000, 10000, 7, 10000, 10000, 0, 10000, 9}, {10000, 6, 9, 10000, 15, 10000, 0, 3}, {10, 6, 10000, 14, 10000, 9, 3, 0}};
        m = new int[][] { /*A*/{0,27,23,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*B*/{27,0,13,38,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*C*/{23,13,0,10000,26,25,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*D*/{10000,38,10000,0,24,10000,25,10000,10000,48,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*E*/{10000,10000,26,24,0,20,30,26,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*F*/{10000,10000,25,10000,20,0,10000,25,42,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*G*/{10000,10000,10000,25,30,10000,0,31,10000,29,900,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*H*/{10000,10000,10000,10000,26,25,31,0,10000,10000,900,10000,900,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*I*/{10000,10000,10000,10000,10000,42,10000,10000,0,10000,10000,10000,900,900,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*J*/{10000,10000,10000,48,10000,10000,29,10000,10000,0,30,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,20,10000,10000,10000,10000},
                 /*K*/{10000,10000,10000,10000,10000,10000,370,320,10000,10000,0,19,10000,10000,10000,20,27,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*L*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,19,0,23,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*M*/{10000,10000,10000,10000,10000,10000,10000,340,220,10000,10000,23,0,38,10000,10000,39,10000,10000,10000,10000,10000,10000,10000,10000,10000},
                 /*N*/{10000,10000,10000,10000,10000,10000,10000,10000,370,10000,10000,10000,38,0,10000,10000,61,10000,10000,610,310,10000,10000,10000,10000,10000},
                 /*O*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,27,10000,10000,10000,10000,0,20,10000,19,20,10000,10000,10000,10000,10000,10000,10000},
                 /*P*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,32,20,10000,10000,10000,20,0,10000,10000,21,10000,10000,10000,10000,480,10000,10000},
                 /*Q*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,27,10000,39,61,10000,36,0,10000,10000,250,10000,10000,10000,400,10000,10000},
                 /*R*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,19,10000,10000,0,10000,10000,10000,200,380,490,10000,10000},
                 /*S*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,20,21,10000,10000,0,10000,10000,10000,10000,330,10000,10000},
                 /*T*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,610,10000,10000,250,10000,10000,0,10000,10000,10000,35,29,10000},
                 /*U*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,610,10000,10000,10000,10000,10000,10000,0,10000,10000,10000,34,10000},
                 /*V*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,20,10000,10000,10000,10000,10000,10000,10000,500,10000,10000,10000,0,40,10000,10000,10000},
                 /*W*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,380,10000,10000,10000,40,0,23,10000,10000},
                 /*X*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,480,400,490,330,35,10000,10000,23,0,57,57},
                 /*Y*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,29,34,10000,10000,57,0,38},
                 /*Z*/{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,71,57,38,0}};


        Log.e("TAG","M: "+m.length);

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (m[i][j] == 10000) {
                    path[i][j] = -1;
                } else {
                    path[i][j] = i;
                }
            }
        }

        for (int i = 0; i < m.length; i++) {
            path[i][i] = i;
        }

        shortpath = shortestpath(m, path);

        int start = 0;
        int end = 16;

        String myPath = end + "";

        while (path[start][end] != start) {
            myPath = path[start][end] + "->" + myPath;
            end = path[start][end];
        }

        Log.e("TAG","PATH: "+myPath);

        myPath = start + "->" + myPath;
        Log.e("TAG","ESTE ES EL CAMINO: "+myPath);

        partes = myPath;
        //parts =partes.split("->"); // escape .

        drawView.init(partes);
        drawViewPoint.init(partes);
        Log.e("TAG", "PARTES PARTES : " + partes);

    }

   /* public void activarBoton() {
        Runnable abrir = new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btPrueba.setVisibility(View.VISIBLE);
                        btPrueba.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("TAG","Dentro del boton");
                                btPrueba.setVisibility(View.GONE);
                                continuar();
                            }
                        });
                    }
                });
            }
            private void continuar() {
                synchronized (hilo) {
                    hilo.notify();
                }
            }
        };
        abrir.run();
    }*/

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

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(Message event) {
        idGrupo = event.getIdGrupo();
        if(idGrupo.equals("correcto")){
            prueba.setImageResource(R.drawable.escaleraamarilla);
        }
    }

}



