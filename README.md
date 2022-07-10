# SAA-Modular

Sinlge Activity Architecture를 구현해본 Sample Project 입니다.

Modular Architecture Sample Project에서 사용한 구조를 그대로 가져와서 사용하였으며,

App Module에 하나의 Activity를 두고, Jetpack Navigation을 사용하여 여러개의 Module을 연결하여 사용하였습니다.

## Navigation

Navigation Module은 별도의 Kotlin 파일이 존재하지 않고, res/navigation/nav_graph.xml 파일만 사용합니다.

Main Activity에 사용되는 최상위 단의 Fragment를 컨트롤하는 navigation이며, SubFragment(Child)에 대한 navigation이 필요한 경우 각 모듈의 내부에 추가로 작성해 두었습니다.

ParentFragment와 ChildFragment를 사용하는 방법은 Main Module에서 확인할 수 있습니다.

MainFragment에서는 ParentFragment를, Home,Move,Text Fragment에서는 ChildFragment를 사용하도록 하였습니다.

## DI
3.2 버전의 Koin을 사용하여 의존성을 주입해 주었습니다.

[Tistory Blog](https://heegs.tistory.com/128 "SAA-Modular Sample")
