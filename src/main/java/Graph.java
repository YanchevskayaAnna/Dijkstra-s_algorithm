public class Graph {
    private final int MAX_VERTS = 20;
    private final int INFINITY = 1000000;
    private Vertex[] vertexList; //Список вершин
    private int[][] adjMat;      //Матрица смежности
    private int nVerts;          //Текущее количество вершин
    private int nTree;           //Количество вершин в дереве
    private DistPar[] sPath;     //Массив данных кратчайих путей
    private int currentVert;     //Текущая вершина
    private int startToCurrent;  //Расстояние до currentVert

    public Graph() {
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;
        for (int j = 0; j < MAX_VERTS; j++)//Матрица смежности заполняется бесконечными расстояними
            for (int k = 0; k < MAX_VERTS; k++)
                adjMat[j][k] = INFINITY;
        sPath = new DistPar[MAX_VERTS];
    }

    public void addVertex(char lab) {
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end, int weight) {
        adjMat[start][end] = weight; //направленный граф
    }

    public void path() { //выбор кратчайших путей
        int startTree = 0;
        vertexList[startTree].isInTree = true;
        nTree = 1; //включение в дерево
        //Перемещение строки расстояний из adjMat в sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DistPar(startTree, tempDist);
        }

        //Пока все вершины не окажутся в дереве
        while (nTree < nVerts) {
            int indexMin = getMin(); //Получение минимума из sPath
            int minDist = sPath[indexMin].distance;

            if (minDist == INFINITY) {
                System.out.println("There are unreachable vertices");
                break;
            } else {
                currentVert = indexMin;
                startToCurrent = sPath[indexMin].distance;
            }
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath();
        }

        displayPaths();

        nTree = 0;
        for (int j = 0; j < nVerts; j++) {
            vertexList[j].isInTree = false;
        }
    }

    private int getMin() {
        int minDist = INFINITY;
        int indexMin = 0;
        for (int j = 1; j < nVerts; j++) { //Для каждой вершины, если она не включена в дерево и её расстояние меньше старого минимума
            if (!vertexList[j].isInTree && sPath[j].distance < minDist) {
                minDist = sPath[j].distance;
                indexMin = j;
            }
        }
        return indexMin;
    }

    public void adjust_sPath() {
        //Обновление данных в массиве кратчайших путей sPath
        int column = 1; //Начальная вершина пропускается
        while (column < nVerts) {
            //Если вершина уже включена в дерево, она пропускается
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            //Вычисление расстояния для одного элемента sPath
            //Получение ребка от currentVert к column
            int currentToFringe = adjMat[currentVert][column];
            //Суммирование расстояний
            int startToFringe = startToCurrent + currentToFringe;
            //Определение расстояний текущего элемента sPath
            int sPathDist = sPath[column].distance;

            //Сравнение расстояний от начальной верщины с элементом sPath
            if (startToFringe < sPathDist) {
                sPath[column].parentVert = currentToFringe; //to do currentVert
                sPath[column].distance = startToFringe;
            }
            column++;
        }
    }

    public void displayPaths() {
        for (int j = 0; j < nVerts; j++) {
            System.out.print(vertexList[j].label + "=");
            if (sPath[j].distance == INFINITY) System.out.printf("inf");
            else System.out.print(sPath[j].distance);
            if (sPath[j].parentVert == INFINITY){
                System.out.print("(inf.)");
            }
            else
            {char parent = vertexList[sPath[j].parentVert].label;
            System.out.print("(" + parent + ")");}
        }
        System.out.println("");
    }
}
