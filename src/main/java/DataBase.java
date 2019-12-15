import java.sql.*;

public class DataBase {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder urlSB = new StringBuilder("jdbc:mysql://"); // polaczenie z MySQL
        urlSB.append("localhost:3306/"); // numer portu
        urlSB.append("rozdania_oskarow?"); // nazwa bazy (bazaWin)
        urlSB.append("useUnicode=true&characterEncoding=utf"); // kodowanie
        urlSB.append("-8&user=root"); // nazwa uzytkownika (root)
        urlSB.append("&password=OsKaR_1998"); // haslo uzytkownika
        urlSB.append("&serverTimezone=CET"); // strefa czasowa (CET)
        String connectionUrl = urlSB.toString();
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            //PODPUNKT A)
            PreparedStatement averageAge = connection.prepareStatement(
                    "select avg(award_aktor_age) from nagrody where award_actor_id like 'AF%';");

            PreparedStatement averageAge1 = connection.prepareStatement(
                    "select avg(award_aktor_age) from nagrody where award_actor_id like 'AM%';");

            PreparedStatement standardDeviation = connection.prepareStatement(
                    "select stddev(award_aktor_age) from nagrody where award_actor_id like 'AF%';");

            PreparedStatement standardDeviation1 = connection.prepareStatement(
                    "select stddev(award_aktor_age) from nagrody where award_actor_id like 'AM%';");
            ResultSet resultAverageAge = averageAge.executeQuery();
            ResultSet resultAverageAge1 = averageAge1.executeQuery();
            ResultSet resultStandardDeviation = standardDeviation.executeQuery();
            ResultSet resultStandardDeviation1 = standardDeviation1.executeQuery();
//                printResultSet(resultAverageAge);
//                printResultSet(resultAverageAge1);
//                printResultSet(resultStandardDeviation);
//                printResultSet(resultStandardDeviation1);

            //PODPUNKT B)
            PreparedStatement fondaAwards = connection.prepareStatement(
                    "select * from aktorzy_aktorki where name like '%Fonda';");

            PreparedStatement fondaAwards1 = connection.prepareStatement(
                    "select count(award_id) from nagrody " +
                            "where award_actor_id='AF100' or award_actor_id='AM179' or award_actor_id='AM37';");
            ResultSet resultFondaAwards = fondaAwards.executeQuery();
            ResultSet resultFondaAwards1 = fondaAwards1.executeQuery();
//                printResultSet(resultFondaAwards);
//                printResultSet(resultFondaAwards1);

            //PODPUNKT C)
            PreparedStatement youngestMan = connection.prepareStatement(
                    "select min(award_aktor_age) from nagrody where award_actor_id like 'AM%';");

            PreparedStatement oldestMan = connection.prepareStatement(
                    "select max(award_aktor_age) from nagrody where award_actor_id like 'AM%';");

            PreparedStatement youngestWoman = connection.prepareStatement(
                    "select min(award_aktor_age) from nagrody where award_actor_id like 'AF%';");

            PreparedStatement oldestWoman = connection.prepareStatement(
                    "select max(award_aktor_age) from nagrody where award_actor_id like 'AF%';");

            ResultSet resultYoungestMan = youngestMan.executeQuery();
            ResultSet resultOldestMan = oldestMan.executeQuery();
            ResultSet resultYoungestWoman = youngestWoman.executeQuery();
            ResultSet resultOldestWoman = oldestWoman.executeQuery();

//                printResultSet(resultYoungestMan);
//                printResultSet(resultOldestMan);
//                printResultSet(resultYoungestWoman);
//                printResultSet(resultOldestWoman);


            //PODPUNKT D)
            PreparedStatement winningWomanPercent = connection.prepareStatement(
                    "select count(award_id)/(select count(award_id) from nagrody)*100 " +
                            "as 'woman percent' from nagrody where award_actor_id like 'AF%';");
            ResultSet resultWinningWomanPercent = winningWomanPercent.executeQuery();
//                printResultSet(resultWinningWomanPercent);

            //PODPUNKT E)
            PreparedStatement moreThanOneOscar = connection.prepareStatement(
                    "select a.name, count(b.award_id) from nagrody b join aktorzy_aktorki a " +
                            "on b.award_actor_id=a.id group by b.award_actor_id having count(b.award_id)>1 " +
                            "order by count(b.award_id) desc;");
            ResultSet resultMoreThanOneOscar = moreThanOneOscar.executeQuery();
//                printResultSet(resultMoreThanOneOscar);

            //PODPUNKT F)
            PreparedStatement ceremonyCounter = connection.prepareStatement(
                    "select count(award_id)/2 as 'Awards ceremony number' from nagrody;");
            ResultSet resultCeremonyCounter = ceremonyCounter.executeQuery();
//                printResultSet(resultCeremonyCounter);

            //PODPUNKT G)
            PreparedStatement threeActorsList = connection.prepareStatement(
                    "select a.name, b.award_year, b.award_actor_movie from" +
                            " aktorzy_aktorki a join nagrody b on a.id=b.award_actor_id " +
                            "and (a.name='Anthony Hopkins' or a.name='Sidney Poitier' or a.name='Jeff Bridges');");
            ResultSet resultThreeActorsList = threeActorsList.executeQuery();
//                printResultSet(resultThreeActorsList);

            //PODPUNKT h)
            PreparedStatement manAllWinners = connection.prepareStatement(
                    "select count(distinct award_actor_id) as 'man winners number' " +
                            "from nagrody where award_actor_id like 'AM%';");

            PreparedStatement womanAllWinners = connection.prepareStatement(
                    "select count(distinct award_actor_id) as 'woman winners number'" +
                            " from nagrody where award_actor_id like 'AF%';");

            ResultSet resultManAllWinners = manAllWinners.executeQuery();
            ResultSet resultWomanAllWinners = womanAllWinners.executeQuery();

//                printResultSet(resultManAllWinners);
//                printResultSet(resultWomanAllWinners);


        } catch (SQLException e) {
            e.printStackTrace();
        }//end of try-catch

    }//end of main

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData(); // metadane o zapytaniu
        int columnsNumber = rsmd.getColumnCount(); // liczba kolumn
        while (resultSet.next()) { // wyswietlenie nazw kolumn i wartosci w rzedach
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1)
                    System.out.print(", ");
                String columnValue = resultSet.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
            }
            System.out.println("");
        }
        System.out.println("");
    }


}//end of class
