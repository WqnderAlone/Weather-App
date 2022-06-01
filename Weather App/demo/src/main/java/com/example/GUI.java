package com.example;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI implements ActionListener{  
    JTextField tf1;
    JButton b1;
    JFrame f;
    String APIKey;
    GUI(String key) {  
        APIKey = key;
        f = new JFrame("Weather");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tf1=new JTextField();  
        tf1.setBounds(50,50,150,20);
         
        b1=new JButton("Get Weather");  
        b1.setBounds(50,200,200,50);  
        
        b1.addActionListener(this);  
        
        f.add(tf1);
        f.add(b1);
        
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.getContentPane().setBackground(Color.YELLOW);
        f.setLayout(null);  
        f.setVisible(true);  
    }    
         
    public void actionPerformed(ActionEvent event) {  
        String city = tf1.getText();
        try {
            showWeather(city);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showWeather(String city) throws Exception{
        f.getContentPane().removeAll();
        f.repaint();

        GeoCoords geo = new GeoCoords(city, APIKey);
        double[] coords = geo.getCoords();
        
        Weather weather = new Weather(coords, false, APIKey);
        
        String weatherDesc = String.valueOf(weather.getWeatherDescription());
        String weatherF = String.valueOf(weather.getTemperature());
        String weatherFeelsLike = String.valueOf(weather.getFeelsLike());
        String windSpeed = String.valueOf(weather.getWindSpeed());
        String windDeg = String.valueOf(weather.getWindDeg());

        JLabel currDescription = new JLabel("Weather Description: " + weatherDesc);
        JLabel currTemp = new JLabel("Current Temperature: " + weatherF);
        JLabel currFeelsLike = new JLabel("Feels Like: " + weatherFeelsLike);
        JLabel currWindSpeed = new JLabel("Wind Speed: " + windSpeed);
        JLabel currWindDeg = new JLabel("Wind Angle: " + windDeg);
        JLabel icon = new JLabel(new ImageIcon(new URL(weather.getWeatherIconURL())));

        currDescription.setBounds(25, 0, 350, 30);
        currTemp.setBounds(25, 50, 350, 30);
        currFeelsLike.setBounds(25, 100, 350, 30);
        currWindSpeed.setBounds(25, 150, 250, 30);
        currWindDeg.setBounds(25, 200, 250, 30);
        icon.setBounds(25, 250, 250, 250);

        f.add(currDescription);
        f.add(currTemp);
        f.add(currFeelsLike);
        f.add(currWindSpeed);
        f.add(currWindDeg);
        f.add(icon);;

        f.setIconImage(new ImageIcon(new URL(weather.getWeatherIconURL())).getImage());

        f.setTitle("Weather in " + city);        

        f.revalidate();
        f.repaint();
    }
}