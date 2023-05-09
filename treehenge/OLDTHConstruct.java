// package treehenge;

// import java.util.ArrayList;
// import java.util.List;

// import java.awt.Color;

// public class THConstructAny {
//     private List<THGeometry> geometry = new ArrayList<THGeometry>();
//     private List<THVector> vertices = new ArrayList<THVector>();

//     public List<THGeometry> getGeometry() {return this.geometry;}
//     public List<THVector> getVertices() {return this.vertices;}

//     public THConstruct(THGeometry... g) {
//         for (THGeometry i : g) {
//             this.geometry.add(i);
//         }
//         this.organizeVertices();
//     }
//     public THConstruct(THConstruct... c) {
//         for (THConstruct i : c) {
//             this.geometry.addAll(i.geometry);
//         }
//         this.organizeVertices();
//     }

//     public void organizeVertices() {
//         for (THGeometry g : this.geometry) {
//             for (THVector v : g.getVertices()) {
//                 if (!this.vertices.contains(v)) {
//                     this.vertices.add(v);
//                 }
//             }
//         }
//     }

    // public void add(THVector v) {
    //     for (THVector vert : this.vertices) {
    //         vert.add(v);
    //     }
    // }

//     public void setColor(Color c) {
//         for (THGeometry g : this.geometry) {
//             g.setFillColor(c);
//         }
//     }

//     public static THGeometry Square(THVector a, THVector b) {
//         if (a.y == b.y) {
//             return new THGeometry(a,
//                 new THVector(a.x, b.y, b.z),
//                 b,
//                 new THVector(b.x, a.y, a.z)
//             );
//         } else {
//             return new THGeometry(a,
//                 new THVector(a.x, b.y, a.z),
//                 b,
//                 new THVector(b.x, a.y, b.z)
//             );
//         }
//     }

//     public static THConstruct RectangularPrism(THVector a, THVector b) {
//         return new THConstruct(
//             THConstruct.Square(a,new THVector(a.x, b.y, b.z)),
//             THConstruct.Square(a,new THVector(b.x, a.y, b.z)),
//             THConstruct.Square(a,new THVector(b.x, b.y, a.z)),
//             THConstruct.Square(b,new THVector(b.x, a.y, a.z)),
//             THConstruct.Square(b,new THVector(a.x, b.y, a.z)),
//             THConstruct.Square(b,new THVector(a.x, a.y, b.z))
//         );
//     }

//     public static THConstruct Cube(THVector v, double sideLength) {
//         return THConstruct.RectangularPrism(v, new THVector(v.x+sideLength,v.y+sideLength,v.z+sideLength));
//     }
// }
