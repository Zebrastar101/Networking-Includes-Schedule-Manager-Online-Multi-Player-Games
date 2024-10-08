import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Student {
    Connection con;
    Statement stm;
    public JTable studentTable;
    ResultSet resultSet;
    public Student(){
        con = Main.myConn;
        //studentTable=new JTable();
        try{
            stm=con.createStatement();
            resultSet=stm.executeQuery("Select*from student WHERE student_id >=1");

            //the below while loop checks if there's elements in the resultSet
            studentTable=buildTable(resultSet);

        }catch(SQLException e){
            e.printStackTrace();

        }

    }
    public JTable buildTable(ResultSet rs) throws SQLException {
        //make colums
        int colNum=rs.getMetaData().getColumnCount();
        ArrayList<Object> perRow=new ArrayList<>();
        ArrayList<ArrayList<Object>> data= new ArrayList<ArrayList<Object>>();
        

        while(rs!=null && rs.next()){
            for(int z=1; z<=colNum; z++){
                perRow.add(rs.getObject(z));
            }
            data.add(perRow);


            perRow=new ArrayList<>();
        }
        if(data.size()!=0){
            Object[][] dataArray= new Object[data.size()][data.get(0).size()];
            for(int r=0; r< dataArray.length;r++){
                for(int c=0; c<dataArray[0].length;c++){
                    dataArray[r] = data.get(r).toArray();
                    //dataArray[r][c]=data.get(r).get(c);

                }
            }
            System.out.println("data for Student table"+Arrays.deepToString(dataArray));
            return makeJTable(dataArray);
        }


        return makeJTable(new Object[0][0]);
    }

    public JTable makeJTable(Object[][] dataArray){
        DefaultTableModel tableModel = new DefaultTableModel(dataArray, new String[]{"Student ID","First Name", "Last Name"}) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all
                // cells false
                return false;
            }
        };

        JTable table = new JTable();
        table.setModel(tableModel);
        table. getTableHeader().setReorderingAllowed(false);

        return table;
    }




    public JTable getStudentTable() {
        return studentTable;
    }
    public JTable addStudent(String fn, String ln) throws SQLException {
        stm.executeUpdate("INSERT INTO student(first_name, last_name) VALUES('"+fn+"','"+ln+"');");
        studentTable=buildTable(stm.executeQuery("Select*from student WHERE student_id >=1"));
        return studentTable;

    }
    public JTable deleteStudent(int id) throws SQLException{
        stm.executeUpdate("DELETE FROM student WHERE student_id="+id+";");
        studentTable=buildTable(stm.executeQuery("Select*from student WHERE student_id >=1"));
        return studentTable;
    }

    public JTable saveStudent(String fn, String ln, int id) throws SQLException {
        stm.executeUpdate("UPDATE student SET first_name='"+fn+"' WHERE student_id="+id+";");
        stm.executeUpdate("UPDATE student SET last_name='"+ln+"' WHERE student_id="+id+";");
        studentTable=buildTable(stm.executeQuery("Select*from student WHERE student_id >=1"));
        return studentTable;
    }


    public JTable importFile(Scanner sc) throws SQLException {
        stm.execute("CREATE TABLE IF NOT EXISTS student(student_id INTEGER NOT NULL AUTO_INCREMENT, first_name TEXT,last_name TEXT, PRIMARY KEY(student_id))");
        String s = sc.nextLine();
        while(!s.equals("STUDENTS:")){
            s = sc.nextLine();
        }
        while (sc.hasNextLine()){
            s = sc.nextLine();

            if(!s.isEmpty()){
                String[] parts=s.split(",");
                stm.executeUpdate("INSERT INTO student(first_name, last_name) VALUES('"+parts[1]+"','"+parts[2]+"');");
            }
            else {
                studentTable=buildTable(stm.executeQuery("Select*from student WHERE student_id >=1"));
                return studentTable;
            }
        }
        return null;
    }






}
