package com.earthpol.socialcredit.sql;

import com.earthpol.socialcredit.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class SQLGetter {

    private final Main instance;

    public SQLGetter(Main instance) {
        this.instance = instance;
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = instance.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS socialpoints " +
                    "(NAME VARCHAR(100),"+
                    "UUID VARCHAR(100)," +
                    "CCPKEY VARCHAR(100)," +
                    "SOCIALPOINTS INTEGER," +
                    "SOCIAL INTEGER," +
                    "PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player){
        try {
            UUID uuid = player.getUniqueId();
            if(!exists(uuid)){
                PreparedStatement ps = instance.SQL.getConnection().prepareStatement("INSERT IGNORE INTO socialpoints "
                        + "(NAME,UUID,CCPKEY,SOCIALPOINTS,SOCIAL) VALUES (?,?,?,?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, uuid.toString());
                ps.setString(3, "000000000");
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.executeUpdate();
                createCCPKey(uuid);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("SELECT * FROM socialpoints WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            //Player Found
            return results.next();
            //Player Not Found

        } catch (SQLException e){
            e.printStackTrace();
        }
        //Player Not Found
        return false;
    }

    public void addSocialPoints(UUID uuid, int socialPoints){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("UPDATE socialpoints SET SOCIALPOINTS=? WHERE UUID=?");
            ps.setInt(1,(getSocialPoints(uuid) + socialPoints));
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeSocialPoints(UUID uuid, int socialPoints){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("UPDATE socialpoints SET SOCIALPOINTS=? WHERE UUID=?");
            ps.setInt(1,(getSocialPoints(uuid) - socialPoints));
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setSocialPoints(UUID uuid, int socialPoints){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("UPDATE socialpoints SET SOCIALPOINTS=? WHERE UUID=?");
            ps.setInt(1,(socialPoints));
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateName(Player player){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("UPDATE socialpoints SET NAME=? WHERE UUID=?");
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getName(Player player){
        try{
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("SELECT NAME FROM socialpoints WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            String name;
            if (rs.next()){
                name = rs.getString("NAME");
                return name;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public void createCCPKey(UUID uuid){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("UPDATE socialpoints SET CCPKEY=? WHERE UUID=?");
            ps.setString(1, generatedString);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getCCPKey(UUID uuid){
        try{
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("SELECT CCPKEY FROM socialpoints WHERE UUID=?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            String voteKey;
            if (rs.next()){
                voteKey = rs.getString("CCPKEY");
                return voteKey;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public int getSocialPoints(UUID uuid){
        try{
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("SELECT SOCIALPOINTS FROM socialpoints WHERE UUID=?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points;
            if (rs.next()){
                points = rs.getInt("SOCIALPOINTS");
                return points;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void emptyTable(){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("TRUNCATE socialpoints");
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removePlayer(UUID uuid){
        try {
            PreparedStatement ps = instance.SQL.getConnection().prepareStatement("DELETE FROM socialpoints WHERE UUID=?");
            ps.setString(1,uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
