package com.my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 参考书目: 挑战程序设计竞赛（第二版）
 *
 * */
public class Main {
    //2.1.5 - BFS - maze shortest route
    private static void mazeRoute(int n, int m, char[][] maze) {
        int startX = -1;
        int startY = -1;
        int endX = -1;
        int endY = -1;
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                if(maze[i][j]=='S') {
                    startX = i;
                    startY = j;
                }
                if(maze[i][j]=='G') {
                    endX = i;
                    endY = j;
                }
            }
        }
        int len = 0;
        int min = bfs(len,startX-1,startY,n,m,maze);
        System.out.println("Min route is: "+min);
    }

    private static int bfs(int len, int i, int j,int n,int m,char[][] maze) {
        if(i==-1 || j==-1 || i==n || j==m || maze[i][j]=='#' || maze[i][j]=='S') return Integer.MAX_VALUE;
        if(maze[i][j]=='G') return len;
        len++;
        int a = bfs(len,i-1,j,n,m,maze);
        int b = bfs(len,i,j-1,n,m,maze);
        int c = bfs(len,i+1,j,n,m,maze);
        int d = bfs(len,i,j+1,n,m,maze);
        return Math.min(a,Math.min(b,Math.min(c,d)));
    }


    //2.1.4 - lake - O(8*n*m)
    private static void lakeCounting(int n, int m, char[][] s) {
        int count = 0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                if(s[i][j]=='W') {
                    checkLake(i,j,n,m,s);
                    count++;
                }
            }
        }
        System.out.println("Lake num: "+count);
    }

    private static void checkLake(int i, int j, int n, int m, char[][] s) {
        s[i][j]='.';
        for(int a=-1;a<2;a++) {
            for(int b=-1;b<2;b++) {
                int y=i+a;
                int x=j+b;
                if(y>-1 && y<n && x>-1 && x<m && s[y][x]=='W')
                    checkLake(i+a,j+b,n,m,s);
            }
        }
    }

    //2.1.4 - O(n^2) - DFS
    private static void partSum(int i,int sum, int[] a, int k) {
        if(i==a.length || sum==k) {
            System.out.println("Result: "+ (sum==k));
            return;
        }
        int temp = sum;
        temp += a[i];
        i++;
        if(temp>k) {
            partSum(i,sum,a,k);
        } else {
            partSum(i,temp,a,k);
        }
    }

    //1.6.3(原题1.1) - O(n^3logn)
    private static void chouqian(int m, int[] l) {
        int n = l.length;
        //1.sort
        Arrays.sort(l);
        //2.find
        int index = -1;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                for(int k=0;k<n;k++) {
                    int last = m-l[i]-l[j]-l[k];
                    index = Arrays.binarySearch(l,last);
                    if(index > -1) {
                        System.out.println("Finded: "+l[i]+" "+l[j]+" "+l[k]+" "+l[index]);
                        return;
                    }
                }
            }
        }
        if(index==-1) System.out.println("Cannot match!");
    }
    //1.6.3改进(原题1.1) - O(n^2logn)
    private static void chouqian2(int m, int[] l) {
        int n = l.length;
        //1.sort
        Arrays.sort(l);
        //2.find
        int index = -1;
        int capacity = n*n;
        ArrayList<Integer> arr = new ArrayList<>(capacity);
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                arr.add(l[i]+l[j]);//还可以去重
            }
        }
        Collections.sort(arr);
        System.out.println(arr);
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < capacity; k++) {
                    index = Arrays.binarySearch(arr.toArray(),m-l[i]-l[j]);
                    if(index>-1) {
                        System.out.println("Finded: "+l[i]+" "+l[j]+" "+arr.get(index));
                        return;
                    }
                }
            }
        }
        if(index==-1) System.out.println("Cannot match!");
    }

    //1.6.2
    private static void ants(int l,int[] x) {
        int n = x.length;
        int minTime = Math.min(x[0], l - x[0]);
        int maxTime = Math.max(x[0], l - x[0]);
        for(int i=1;i<n;i++) {
            minTime = Math.max(minTime,Math.min(x[i],l-x[i]));
        }
        for(int i=1;i<n;i++) {
            maxTime = Math.max(Math.max(x[i], l - x[i]),maxTime);
        }
        System.out.println("Min time: "+minTime+"s");
        System.out.println("Max time: "+maxTime+"s");
    }

    //1.6.1
    private static void triangle(int[] l) {
        int n = l.length;
        //1.sort
        Arrays.sort(l);//O(nlogn)
        //2.find
        int i=n-1;
        while(i>1) {
            if(l[i]>l[i-1]+l[i-2]) {
                i--;
                continue;
            }
            int perimeter = l[i]+l[i-1]+l[i-2];
            System.out.println("Max perimeter is: "+l[i]+"+"+l[i-1]+"+"+l[i-2]+"="+perimeter);
            break;
        }
    }

    public static void main(String[] args) {
        int[] a1 = {3,2,4,5,10};
//        triangle(a1);

        int l2 = 10;
        int[] x2 = {2,6,7};
//        ants(l2,x2);

        int m3 = 10;
        int[] k3 = {1,3,5};
//        chouqian(m3,k3);
//        chouqian2(m3,k3);

        int[] a4 = {1,2,4,7};
        int k4 = 14;
//        partSum(0,0,a4,k4);

        int n5 = 10;
        int m5 = 10;
        char[][] lake5= {
                "W........WW.".toCharArray(),
                ".WWW.....WWW".toCharArray(),
                "....WW...WW.".toCharArray(),
                ".........WW.".toCharArray(),
                ".........W..".toCharArray(),
                "..W......W..".toCharArray(),
                ".W.W.....WW.".toCharArray(),
                "W.W.W.....W.".toCharArray(),
                ".W.W......W.".toCharArray(),
                "..W.......W.".toCharArray()
        };
//        lakeCounting(n5,m5,lake5);

        int n6 = 10;
        int m6 = 10;
        //'#'墙壁，'.'通道，'S'起点，'G'终点
        char[][] maze6= {
                "#S######.#".toCharArray(),
                "......#..#".toCharArray(),
                ".#.##.##.#".toCharArray(),
                ".#........".toCharArray(),
                "##.##.####".toCharArray(),
                "....#....#".toCharArray(),
                ".#######.#".toCharArray(),
                "....#.....".toCharArray(),
                ".####.###.".toCharArray(),
                "....#...G#".toCharArray()
        };
        mazeRoute(n6,m6,maze6);
    }

}
