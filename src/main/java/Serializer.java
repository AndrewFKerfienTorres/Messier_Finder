import java.io.*;

public class Serializer {

    public static void main(String[] args) {
        Catalogue c = Catalogue.getInstance();

        c.add(new CelestialObject("M1",420,8.4,"Crab Nebula","Taurus","Super-nova remnant","NGC 1952",4.9));
        c.add(new CelestialObject("M2",16,6.5,"—","Aquarius","Globular cluster","NGC 7089",33));
        c.add(new CelestialObject("M3",18,6.2,"—","Canes Venatici","Globular cluster","NGC 5272",33.9));
        c.add(new CelestialObject("M4",26,5.6,"Spider Globular Cluster","Scorpius","Globular cluster","NGC 6121",7.2));
        c.add(new CelestialObject("M5",23,5.6,"Rose Cluster","Serpens","Globular cluster","NGC 5904",24.5));
        c.add(new CelestialObject("M6",25,4.2,"Butterfly Cluster","Scorpius","Open cluster","NGC 6405",1.6));
        c.add(new CelestialObject("M7",80,3.3,"Ptolemy's Cluster","Scorpius","Open cluster","NGC 6475",0.65));
        c.add(new CelestialObject("M8",90,4.6,"Lagoon Nebula","Sagittarius","Nebula with cluster","NGC 6523",4.1));
        c.add(new CelestialObject("M9",9.3,7.7,"—","Ophiuchus","Globular cluster","NGC 6333",25.8));
        c.add(new CelestialObject("M10",20,6.6,"—","Ophiuchus","Globular cluster","NGC 6254",14.3));
        c.add(new CelestialObject("M11",22.8,5.8,"Wild Duck Cluster","Scutum","Open cluster","NGC 6705",6.2));
        c.add(new CelestialObject("M12",16,6.7,"—","Ophiuchus","Globular cluster","NGC 6218",15.7));
        c.add(new CelestialObject("M13",20,5.8,"Great Hercules Cluster","Hercules","Globular cluster","NGC 6205",22.2));
        c.add(new CelestialObject("M14",11,7.6,"—","Ophiuchus","Globular cluster","NGC 6402",30.30));
        c.add(new CelestialObject("M15",18,6.2,"Great Pegasus Cluster","Pegasus","Globular cluster","NGC 7078",330));
        c.add(new CelestialObject("M16",70,6.4,"Eagle Nebula","Serpens","H II region nebula with cluster","NGC 6611",70));
        c.add(new CelestialObject("M17",11,6,"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula","Sagittarius","HII region nebula with cluster","NGC 6618",50));
        c.add(new CelestialObject("M18",9.8,7.5,"Black Swan Cluster","Sagittarius","Open cluster","NGC 6613",4.90));
        c.add(new CelestialObject("M19",17,6.8,"—","Ophiuchus","Globular cluster","NGC 6273",28.70));
        c.add(new CelestialObject("M20",28,6.3,"Trifid Nebula","Sagittarius","H II region nebula with cluster","NGC 6514",5.20));
        c.add(new CelestialObject("M21",14,6.5,"Webb's Cross Cluster","Sagittarius","Open cluster","NGC 6531",4.250));
        c.add(new CelestialObject("M22",32,5.1,"Great Sagittarius Cluster","Sagittarius","Globular cluster","NGC 6656",9.60));
        c.add(new CelestialObject("M23",35,5.5,"—","Sagittarius","Open cluster","NGC 6494",2.150));
        c.add(new CelestialObject("M24",2,2.5,"Small Sagittarius Star Cloud","Sagittarius","Milky Way star cloud","IC 4715",100));
        c.add(new CelestialObject("M25",36,4.6,"—","Sagittarius","Open cluster","IC 4725",20));
        c.add(new CelestialObject("M26",14,8,"—","Scutum","Open cluster","NGC 6694",50));
        c.add(new CelestialObject("M27",8,7.4,"Dumbbell Nebula","Vulpecula","Planetary nebula","NGC 6853",1.1480));
        c.add(new CelestialObject("M28",11.2,6.8,"—","Sagittarius","Globular cluster","NGC 6626",17.90));
        c.add(new CelestialObject("M29",7,7.1,"Cooling Tower Cluster","Cygnus","Open cluster","NGC 6913",7.20));
        c.add(new CelestialObject("M30",12,7.2,"Jellyfish Cluster","Capricornus","Globular cluster","NGC 7099",27.80));
        c.add(new CelestialObject("M31",3.17,3.4,"Andromeda Galaxy","Andromeda","Spiral galaxy","NGC 224",24300));
        c.add(new CelestialObject("M32",8.7,8.1,"Andromeda Satellite #1","Andromeda","Dwarf elliptical galaxy","NGC 221",24100));
        c.add(new CelestialObject("M33",70.8,5.7,"Triangulum/Pinwheel Galaxy","Triangulum","Spiral galaxy","NGC 598",23800));
        c.add(new CelestialObject("M34",35,5.5,"Spiral Cluster","Perseus","Open cluster","NGC 1039",1.50));
        c.add(new CelestialObject("M35",28,5.3,"Shoe-Buckle Cluster","Gemini","Open cluster","NGC 2168",2.80));
        c.add(new CelestialObject("M36",12,6.3,"Pinwheel Cluster","Auriga","Open cluster","NGC 1960",4.10));
        c.add(new CelestialObject("M37",24,6.2,"Salt and Pepper Cluster","Auriga","Open cluster","NGC 2099",4.5110));
        c.add(new CelestialObject("M38",21,7.4,"Starfish Cluster","Auriga","Open cluster","NGC 1912",4.20));
        c.add(new CelestialObject("M39",29,4.6,"Pyramid Cluster","Cygnus","Open cluster","NGC 7092",0.82440));
        c.add(new CelestialObject("M40",51.7,8.4,"Winnecke 4","Ursa Major","Optical Double","—",0.510));
        c.add(new CelestialObject("M41",38,4.5,"Little Beehive Cluster","Canis Major","Open cluster","NGC 2287",2.30));
        c.add(new CelestialObject("M42",65,4,"Great Orion Nebula","Orion","H II region nebula","NGC 1976",1.3240));
        c.add(new CelestialObject("M43",20,9,"De Mairan's Nebula","Orion","H II region nebula (part of the Orion Nebula)","NGC 1982",1.60));
        c.add(new CelestialObject("M44",95,3.7,"Beehive Cluster or Praesepe","Cancer","Open cluster","NGC 2632",0.5770));
        c.add(new CelestialObject("M45",2,1.6,"Pleiades, Seven Sisters or Subaru","Taurus","Open cluster","—",0.390));
        c.add(new CelestialObject("M46",22.8,6,"—","Puppis","Open cluster","NGC 2437",5.40));
        c.add(new CelestialObject("M47",30,4.4,"—","Puppis","Open cluster","NGC 2422",1.60));
        c.add(new CelestialObject("M48",30,5.5,"—","Hydra","Open cluster","NGC 2548",1.50));
        c.add(new CelestialObject("M49",10.2,8.4,"—","Virgo","Elliptical galaxy","NGC 4472",536000));
        c.add(new CelestialObject("M50",16,5.9,"Heart-Shaped Cluster","Monoceros","Open cluster","NGC 2323",3.20));
        c.add(new CelestialObject("M51",11.2,8.4,"Whirlpool Galaxy","Canes Venatici","Spiral galaxy","NGC 5194,NGC 5195",190000));
        c.add(new CelestialObject("M52",13,7.3,"Scorpion Cluster","Cassiopeia","Open cluster","NGC 7654",50));
        c.add(new CelestialObject("M53",13,7.6,"—","Coma Berenices","Globular cluster","NGC 5024",580));
        c.add(new CelestialObject("M54",12,7.6,"—","Sagittarius","Globular cluster","NGC 6715",87.40));
        c.add(new CelestialObject("M55",19,6.3,"Specter Cluster","Sagittarius","Globular cluster","NGC 6809",17.60));
        c.add(new CelestialObject("M56",8.8,8.3,"—","Lyra","Globular cluster","NGC 6779",32.90));
        c.add(new CelestialObject("M57",230,8.8,"Ring Nebula","Lyra","Planetary nebula","NGC 6720",1.60));
        c.add(new CelestialObject("M58",5.9,9.7,"—","Virgo","Barred Spiral galaxy","NGC 4579",630000));
        c.add(new CelestialObject("M59",5.4,9.6,"—","Virgo","Elliptical galaxy","NGC 4621",550000));
        c.add(new CelestialObject("M60",7.4,8.8,"—","Virgo","Elliptical galaxy","NGC 4649",510000));
        c.add(new CelestialObject("M61",6.5,9.7,"Swelling Spiral Galaxy","Virgo","Spiral galaxy","NGC 4303",502000));
        c.add(new CelestialObject("M62",15,6.5,"Flickering Globular","Ophiuchus","Globular cluster","NGC 6266",22.20));
        c.add(new CelestialObject("M63",12.6,8.6,"Sunflower Galaxy","Canes Venatici","Spiral galaxy","NGC 5055",370000));
        c.add(new CelestialObject("M64",10.7,8.5,"Black Eye Galaxy","Coma Berenices","Spiral galaxy","NGC 4826",220000));
        c.add(new CelestialObject("M65",8.7,9.3,"Leo Triplet","Leo","Barred Spiral galaxy","NGC 3623",410000));
        c.add(new CelestialObject("M66",9.1,8.9,"Leo Triplet","Leo","Barred Spiral galaxy","NGC 3627",310000));
        c.add(new CelestialObject("M67",30,6.1,"King Cobra or Golden Eye Cluster","Cancer","Open cluster","NGC 2682",2.610));
        c.add(new CelestialObject("M68",11,7.8,"—","Hydra","Globular cluster","NGC 4590",33.60));
        c.add(new CelestialObject("M69",10.8,7.6,"—","Sagittarius","Globular cluster","NGC 6637",29.70));
        c.add(new CelestialObject("M70",8,7.9,"—","Sagittarius","Globular cluster","NGC 6681",29.40));
        c.add(new CelestialObject("M71",7.2,8.2,"Angelfish Cluster","Sagitta","Globular cluster","NGC 6838",130));
        c.add(new CelestialObject("M72",6.6,9.3,"—","Aquarius","Globular cluster","NGC 6981",53.40));
        c.add(new CelestialObject("M73",2.8,9,"—","Aquarius","Asterism","NGC 6994",2.50));
        c.add(new CelestialObject("M74",10.5,9.4,"Phantom Galaxy[91]","Pisces","Spiral galaxy","NGC 628",240000));
        c.add(new CelestialObject("M75",6.8,8.5,"—","Sagittarius","Globular cluster","NGC 6864",67.50));
        c.add(new CelestialObject("M76",2.7,10.1,"Little Dumbbell Nebula","Perseus","Planetary nebula","NGC 650,NGC 651",2.50));
        c.add(new CelestialObject("M77",7.1,8.9,"Cetus A or Squid Galaxy","Cetus","Spiral galaxy","NGC 1068",470000));
        c.add(new CelestialObject("M78",8,8.3,"—","Orion","Diffuse nebula","NGC 2068",1.60));
        c.add(new CelestialObject("M79",8.7,7.7,"—","Lepus","Globular cluster","NGC 1904",410));
        c.add(new CelestialObject("M80",10,7.3,"—","Scorpius","Globular cluster","NGC 6093",32.60));
        c.add(new CelestialObject("M81",26.9,6.9,"Bode's Galaxy","Ursa Major","Spiral galaxy","NGC 3031",114000));
        c.add(new CelestialObject("M82",11.2,8.4,"Cigar Galaxy","Ursa Major","Starburst galaxy","NGC 3034",107000));
        c.add(new CelestialObject("M83",12.9,7.6,"Southern Pinwheel Galaxy","Hydra","Barred Spiral galaxy","NGC 5236",147000));
        c.add(new CelestialObject("M84",6.5,9.1,"—","Virgo","Lenticular galaxy","NGC 4374",570000));
        c.add(new CelestialObject("M85",7.1,9.1,"—","Coma Berenices","Lenticular galaxy","NGC 4382",560000));
        c.add(new CelestialObject("M86",8.9,8.9,"—","Virgo","Lenticular galaxy","NGC 4406",490000));
        c.add(new CelestialObject("M87",7.2,8.6,"Virgo A","Virgo","Elliptical galaxy","NGC 4486",518700));
        c.add(new CelestialObject("M88",6.9,9.6,"—","Coma Berenices","Spiral galaxy","NGC 4501",390000));
        c.add(new CelestialObject("M89",5.1,9.8,"—","Virgo","Elliptical galaxy","NGC 4552",470000));
        c.add(new CelestialObject("M90",9.5,9.5,"—","Virgo","Spiral galaxy","NGC 4569",559000));
        c.add(new CelestialObject("M91",5.4,10.2,"—","Coma Berenices","Barred Spiral galaxy","NGC 4548",470000));
        c.add(new CelestialObject("M92",14,6.4,"—","Hercules","Globular cluster","NGC 6341",26.70));
        c.add(new CelestialObject("M93",10,6,"Critter Cluster","Puppis","Open cluster","NGC 2447",3.60));
        c.add(new CelestialObject("M94",11.2,8.2,"Crocodile Eye or Cat's Eye Galaxy","Canes Venatici","Spiral galaxy","NGC 4736",147000));
        c.add(new CelestialObject("M95",3.1,9.7,"—","Leo","Barred Spiral galaxy","NGC 3351",312000));
        c.add(new CelestialObject("M96",7.6,9.2,"—","Leo","Spiral galaxy","NGC 3368",280000));
        c.add(new CelestialObject("M97",3.4,9.9,"Owl Nebula","Ursa Major","Planetary nebula","NGC 3587",2.030));
        c.add(new CelestialObject("M98",9.8,10.1,"—","Coma Berenices","Spiral galaxy","NGC 4192",444000));
        c.add(new CelestialObject("M99",5.4,9.9,"St. Catherine's Wheel","Coma Berenices","Spiral galaxy","NGC 4254",447000));
        c.add(new CelestialObject("M100",7.4,9.3,"Mirror Galaxy","Coma Berenices","Spiral galaxy","NGC4321",550000));
        c.add(new CelestialObject("M101",28.8,7.9,"Pinwheel Galaxy","Ursa Major","Spiral galaxy","NGC5457",191000));
        c.add(new CelestialObject("M102",4.7,9.9,"Spindle Galaxy","Draco","Lenticular galaxy","NGC5866",500000));
        c.add(new CelestialObject("M103",6,7.4,"—","Cassiopeia","Open cluster","NGC581",100));
        c.add(new CelestialObject("M104",9,8,"Sombrero Galaxy","Virgo","Spiral galaxy","NGC4594",287000));
        c.add(new CelestialObject("M105",5.4,9.3,"—","Leo","Elliptical galaxy","NGC3379",304000));
        c.add(new CelestialObject("M106",18.6,8.4,"—","Canes Venatici","Spiral galaxy","NGC4258",222000));
        c.add(new CelestialObject("M107",10,7.9,"Crucifix Cluster","Ophiuchus","Globular cluster","NGC6171",20.90));
        c.add(new CelestialObject("M108",8.7,10,"Surfboard Galaxy","Ursa Major","Barred Spiral galaxy","NGC3556",460000));
        c.add(new CelestialObject("M109",7.6,9.8,"Vacuum Cleaner Galaxy","Ursa Major","Barred Spiral galaxy","NGC3992",595000));
        c.add(new CelestialObject("M110",21.9,8.5,"Andromeda Satellite #2","Andromeda","Dwarf elliptical galaxy","NGC205",26000));

        //actual serializing part

        String path = System.getProperty("user.dir") + File.separator +"src" + File.separator +"main" + File.separator +"resources";

        for (CelestialObject o : c){
            System.out.println(o.getMessierIndex());
        }



        try{
            ObjectOutputStream sc = new ObjectOutputStream(new FileOutputStream(path + File.separator +"catalogue.ser"));
            sc.writeObject(c);
            sc.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
    
    
    
    
    
}
