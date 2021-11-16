package com.my;

import java.util.*;

/**
 * 参考书目: 挑战程序设计竞赛（第二版）
 *
 * */
public class Main {
    //2.1.5 - BFS - maze shortest route
    //BFS核心是利用队列来遍历, 有多少个格子就有多少种状态O(n*m)
    public static int MAX_ROUTE = 100;
    private static void mazeRoute(int n, int m, char[][] maze) {
        int startX = -1;
        int startY = -1;
        int endX = -1;
        int endY = -1;
        int[][] temp = new int[n][m];
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                temp[i][j] = MAX_ROUTE;
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
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[]{startX, startY});
        temp[startX][startY] = 0;
        bfs(len,n,m,maze,temp,que);
        System.out.println("Min route is: "+temp[endX][endY]);
        print2DArray(maze,n,m);
        print2DArray(temp,n,m);
    }

    private static void bfs(int len,int n,int m,char[][] maze,int[][] temp,Queue<int[]> que) {
        while(!que.isEmpty()) {
            int[] point = que.poll();
            int x = point[0];
            int y = point[1];
            temp[x][y] = len;
            len++;
            if(maze[x][y]=='G') {
                break;
            }
            int[] ox = {0,1,0,-1};
            int[] oy = {1,0,-1,0};
            for(int i=0;i<4;i++) {
                int newX = x+ox[i];
                int newY = y+oy[i];
                if(newX!=-1 && newY!=-1 && newX<n && newY<m && maze[newX][newY]!='#' && maze[newX][newY]!='S' && temp[newX][newY]==MAX_ROUTE) {
                    que.add(new int[]{newX,newY});
                    bfs(len,n,m,maze,temp,que);
                }
            }
        }
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

    public static void print2DArray(char[][] arr, int w, int h) {
        for(int i=0;i<w;i++) {
            for(int j=0;j<h;j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
    public static void print2DArray(int[][] arr, int w, int h) {
        for(int i=0;i<w;i++) {
            for(int j=0;j<h;j++) {
                System.out.printf("%-5d",arr[i][j]);
            }
            System.out.println();
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
