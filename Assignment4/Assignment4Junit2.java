import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Assignment4Junit2 {

    @Test
    public void question01_task01_constructor() {
        Polynomial p1 = new Polynomial(new int[]{3, 1, 4, 0, 3});
        Polynomial p2 = new Polynomial(new int[]{0, 0, 1, 3, 3, 1});
    }

    @Test
    public void question01_task02_toString1() {
        Polynomial p1 = new Polynomial(new int[]{3, 1, 4, 0, 3});
        assertEquals("0:3 1:1 2:4 4:3", p1.toString().trim());
    }

    @Test
    public void question01_task03_toString2() {
        Polynomial p1 = new Polynomial(new int[]{0, 2, -3, 0, 4, 0});
        assertEquals("1:2 2:-3 4:4", p1.toString().trim());
    }

    @Test
    public void question01_task04_add1() throws NoSuchMethodException {
        Polynomial p1 = new Polynomial(new int[]{3, 1, 4, 0, 3});
        Polynomial p2 = new Polynomial(new int[]{0, 0, 1, 3, 3, 1});
        Polynomial p3 = new Polynomial(new int[]{0, -2, 0, -2});

        Polynomial p4 = p1.add(p2);
        assertEquals("0:3 1:1 2:5 3:3 4:6 5:1", p4.toString().trim());
        assertEquals("0:3 1:1 2:5 3:3 4:6 5:1", p1.toString().trim());

        Polynomial p5 = p1.add(p3);
        assertEquals("0:3 1:-1 2:5 3:1 4:6 5:1", p5.toString().trim());
        assertEquals("0:3 1:-1 2:5 3:1 4:6 5:1", p1.toString().trim());

        Class<Polynomial> clazz = Polynomial.class;
        Method method = clazz.getDeclaredMethod("add", Polynomial.class);
        assertTrue("Polynomial.add is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question01_task05_add2() throws NoSuchMethodException {
        Polynomial p1 = new Polynomial(new int[]{3, 0, 6, 0, 2});
        Polynomial p2 = new Polynomial(new int[]{-3, 0, -2, 3, -2, 1});

        Polynomial p3 = Polynomial.add(p1, p2);
        assertEquals("2:4 3:3 5:1", p3.toString().trim());

        // To ensure p3 is a new Polynomial, p1 and p2 not change
        assertEquals("0:3 2:6 4:2", p1.toString().trim());
        assertEquals("0:-3 2:-2 3:3 4:-2 5:1", p2.toString().trim());

        Class<Polynomial> clazz = Polynomial.class;
        Method method = clazz.getDeclaredMethod("add", Polynomial.class, Polynomial.class);
        assertTrue("Polynomial.add is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question01_task06_minus1() throws NoSuchMethodException {
        Polynomial p1 = new Polynomial(new int[]{3, 1, 4, 0, 3});
        Polynomial p2 = new Polynomial(new int[]{3, 0, 4, 3, 3, 1});

        Polynomial p3 = p1.minus(p2);
        assertEquals("1:1 3:-3 5:-1", p3.toString().trim());
        assertEquals("1:1 3:-3 5:-1", p1.toString().trim());

        Class<Polynomial> clazz = Polynomial.class;
        Method method = clazz.getDeclaredMethod("minus", Polynomial.class);
        assertTrue("Polynomial.minus is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question01_task07_minus2() throws NoSuchMethodException {
        Polynomial p1 = new Polynomial(new int[]{3, 1, 6, 0, 2, 1});
        Polynomial p2 = new Polynomial(new int[]{-3, 0, -2, 3, -2, 1});

        Polynomial p3 = Polynomial.minus(p1, p2);
        assertEquals("0:6 1:1 2:8 3:-3 4:4", p3.toString().trim());

        // To ensure p3 is a new Polynomial, p1 and p2 not change
        assertEquals("0:3 1:1 2:6 4:2 5:1", p1.toString().trim());
        assertEquals("0:-3 2:-2 3:3 4:-2 5:1", p2.toString().trim());

        Class<Polynomial> clazz = Polynomial.class;
        Method method = clazz.getDeclaredMethod("minus", Polynomial.class, Polynomial.class);
        assertTrue("Polynomial.minus is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question01_task99_bonus_multiply() throws InvocationTargetException, IllegalAccessException {
        Class<Polynomial> clazz = Polynomial.class;
        Method method = null;
        Method staticMethod = null;

        try {
            method = clazz.getDeclaredMethod("multiply", Polynomial.class);
            staticMethod = clazz.getDeclaredMethod("multiply", Polynomial.class, Polynomial.class);
        } catch (NoSuchMethodException e) {
            fail("no Polynomial.multiply(Polynomial) or Polynomial.multiply(Polynomial, Polynomial). bonus is not finish");
        }

        assertTrue("Polynomial.multiply(Polynomial) is not public", Modifier.isPublic(method.getModifiers()));
        assertTrue("Polynomial.multiply(Polynomial, Polynomial) is not public", Modifier.isPublic(staticMethod.getModifiers()));

        Polynomial p1 = new Polynomial(new int[]{3, 2, -1});
        Polynomial p2 = new Polynomial(new int[]{-2, 0, 4});

        Polynomial p3 = (Polynomial) staticMethod.invoke(null, p1, p2);
        Polynomial p4 = (Polynomial) method.invoke(p1, p2);

        assertEquals("0:-6 1:-4 2:14 3:8 4:-4", p3.toString().trim());
        assertEquals("0:-6 1:-4 2:14 3:8 4:-4", p4.toString().trim());
        assertEquals("0:-6 1:-4 2:14 3:8 4:-4", p1.toString().trim());
    }

    @Test
    public void question02_task01_district_fields() {
        Class clazz = District.class;
        assertTrue("District is not a Enum", clazz.isEnum());

        try {
            Field name = clazz.getDeclaredField("name");
            assertEquals("District.name is not String", name.getType(), String.class);
            assertTrue("District.name is not private", Modifier.isPrivate(name.getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("cannot find name field in District");
        }

        try {
            Field stationCount = clazz.getDeclaredField("stationCount");
            assertEquals("District.stationCount is not int", stationCount.getType(), int.class);
            assertTrue("District.stationCount is not private", Modifier.isPrivate(stationCount.getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("cannot find stationCount field in District");
        }
    }

    @Test
    public void question02_task02_district_enum() throws NoSuchFieldException, IllegalAccessException {
        Class clazz = District.class;
        Field name = clazz.getDeclaredField("name");
        Field stationCount = clazz.getDeclaredField("stationCount");
        name.setAccessible(true);
        stationCount.setAccessible(true);

        assertEquals("Bao'an", name.get(District.BAOAN));
        assertEquals("Futian", name.get(District.FUTIAN));
        assertEquals("Longgang", name.get(District.LONGGANG));
        assertEquals("Longhua", name.get(District.LONGHUA));
        assertEquals("Luohu", name.get(District.LUOHU));
        assertEquals("Nanshan", name.get(District.NANSHAN));

        assertEquals(25, stationCount.getInt(District.BAOAN));
        assertEquals(51, stationCount.getInt(District.FUTIAN));
        assertEquals(22, stationCount.getInt(District.LONGGANG));
        assertEquals(9, stationCount.getInt(District.LONGHUA));
        assertEquals(23, stationCount.getInt(District.LUOHU));
        assertEquals(49, stationCount.getInt(District.NANSHAN));
    }

    @Test
    public void question02_task03_station_fields() {
        Class clazz = Station.class;

        String[] names = {"name", "district", "latitude", "longitude", "next"};
        Class[] classes = {String.class, District.class, double.class, double.class, Station.class};

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Class type = classes[i];
            try {
                Field field = clazz.getDeclaredField(name);
                assertEquals("Station." + name + " is not " + type.getName(), field.getType(), type);
                assertTrue("Station." + name + " is not private", Modifier.isPrivate(field.getModifiers()));
            } catch (NoSuchFieldException e) {
                fail("cannot find " + name + " field in Station");
            }
        }

    }

    @Test
    public void question02_task04_station_constructor() throws NoSuchMethodException {
        Station s1 = new Station();
        Station s2 = new Station("test", District.NANSHAN, 23.4, 123.4);

        Class<Station> clazz = Station.class;
        assertTrue("Constructor of Station is not public", Modifier.isPublic(
                clazz.getDeclaredConstructor().getModifiers()
        ));
        assertTrue("Constructor of Station is not public", Modifier.isPublic(
                clazz.getDeclaredConstructor(String.class, District.class, double.class, double.class).getModifiers()
        ));
    }

    @Test
    public void question02_task05_station_equals1() {
        Station s1 = new Station("test", District.NANSHAN, 23.4, 123.4);
        Station s2 = new Station("test", District.NANSHAN, 23.4, 123.4);

        assertTrue(s1.equals(s2));

        s1.setNext(s2);
        assertTrue(s1.equals(s2));

        Station s3 = new Station("test1", District.NANSHAN, 23.4, 123.4);
        Station s4 = new Station("test", District.FUTIAN, 23.4, 123.4);
        Station s5 = new Station("test", District.NANSHAN, 23.45, 123.4);
        Station s6 = new Station("test", District.NANSHAN, 23.4, 23.4);

        assertFalse(s1.equals(s3));
        assertFalse(s1.equals(s4));
        assertFalse(s1.equals(s5));
        assertFalse(s1.equals(s6));
    }

    @Test
    public void question02_task06_station_equals2() {
        Station s1 = new Station("test", District.NANSHAN, 23.4, 123.4);
        assertFalse(s1.equals(null));
    }

    @Test
    public void question02_task07_busline_fields() {
        Class clazz = BusLine.class;

        String[] names = {"head", "tail", "number", "size"};
        Class[] classes = {Station.class, Station.class, String.class, int.class};

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Class type = classes[i];
            try {
                Field field = clazz.getDeclaredField(name);
                assertEquals("BusLine." + name + " is not " + type.getName(), field.getType(), type);
                assertTrue("BusLine." + name + " is not private", Modifier.isPrivate(field.getModifiers()));
            } catch (NoSuchFieldException e) {
                fail("cannot find " + name + " field in BusLine");
            }
        }

        assertEquals("You defined other fields in BusLine", 4, clazz.getDeclaredFields().length);

    }

    @Test
    public void question02_task08_busline_constructor() throws NoSuchMethodException {
        checkField();

        BusLine s1 = new BusLine();
        BusLine s2 = new BusLine("78");

        Class<BusLine> clazz = BusLine.class;
        assertTrue("Constructor of BusLine is not public", Modifier.isPublic(
                clazz.getDeclaredConstructor().getModifiers()
        ));
        assertTrue("Constructor of BusLine is not public", Modifier.isPublic(
                clazz.getDeclaredConstructor(String.class).getModifiers()
        ));
    }

    @Test
    public void question02_task09_busline_addStation1() throws NoSuchMethodException {
        checkField();

        BusLine busLine = new BusLine("74");
        int size = 0;
        assertEquals(size, busLine.size());

        List<Station> stations = testStations1();
        for (Station station : stations) {
            busLine.addStation(station);
            size++;
            assertEquals(size, busLine.size());
            assertEquals(station.getName(), busLine.getTail().getName());
        }

        Class<BusLine> clazz = BusLine.class;
        Method method = clazz.getDeclaredMethod("addStation", Station.class);
        assertTrue("BusLine.addStation is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question02_task10_busline_addStation2() {
        checkField();

        BusLine busLine = new BusLine("38");
        int size = 0;
        assertEquals(size, busLine.size());

        List<Station> stations = testStations2();
        for (Station station : stations) {
            busLine.addStation(station);
            size++;
            assertEquals(size, busLine.size());
            assertEquals(station.getName(), busLine.getTail().getName());
        }

    }


    @Test
    public void question02_task11_busline_isEmpty() throws NoSuchMethodException {
        checkField();

        BusLine busLine = new BusLine("74");
        assertTrue(busLine.isEmpty());

        List<Station> list = testStations1();
        for (Station station : list) {
            busLine.addStation(station);
            assertFalse(busLine.isEmpty());
        }

        Class<BusLine> clazz = BusLine.class;
        Method method = clazz.getDeclaredMethod("isEmpty");
        assertTrue("BusLine.isEmpty is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question02_task12_busline_size() throws NoSuchMethodException {
        checkField();

        BusLine busLine = new BusLine("74");
        int size = 0;
        assertEquals(size, busLine.size());

        List<Station> list = testStations1();
        for (Station station : list) {
            busLine.addStation(station);
            size++;
            assertEquals(size, busLine.size());
        }

        Class<BusLine> clazz = BusLine.class;
        Method method = clazz.getDeclaredMethod("size");
        assertTrue("BusLine.size is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question02_task13_busline_printStation1() throws NoSuchMethodException {
        checkField();

        BusLine busLine = new BusLine("74");

        List<Station> list = testStations1();
        for (Station station : list) {
            busLine.addStation(station);
        }

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream(1024 * 1024);
        System.setOut(new PrintStream(byteArray));

        busLine.printStation();
        assertEquals("Changlingpi University Town Xili Chaguang Taoyuan Nanshan", new String(byteArray.toByteArray()).trim());
        System.out.close();

        Class<BusLine> clazz = BusLine.class;
        Method method = clazz.getDeclaredMethod("printStation");
        assertTrue("BusLine.printStation is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question02_task14_busline_printStation2() {
        checkField();

        BusLine busLine = new BusLine("38");

        List<Station> list = testStations2().stream().limit(8).collect(Collectors.toList());
        for (Station station : list) {
            busLine.addStation(station);
        }

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream(1024 * 1024);
        System.setOut(new PrintStream(byteArray));

        busLine.printStation();
        assertEquals("Xiangmei North Jingtian Lianhua West Civic Center Convention and Exhibition Center Station Gangxia Huaqiang South Ludancun", new String(byteArray.toByteArray()).trim());
        System.out.close();
    }

    @Test
    public void question02_task15_busline_nearestStation1() throws NoSuchMethodException {
        checkField();

        Station s1 = new Station("TestStation1", District.NANSHAN, 0, 0);
        Station s2 = new Station("TestStation2", District.LUOHU, 0, 1);
        Station s3 = new Station("TestStation3", District.LONGHUA, 0, 2);
        Station s4 = new Station("TestStation4", District.BAOAN, 0, 3);
        Station s5 = new Station("TestStation5", District.FUTIAN, 1, 3);

        Station s6 = new Station("TestStation6", District.LUOHU, 0, 1.2);
        Station s7 = new Station("TestStation7", District.LUOHU, 0, 1.8);
        Station s8 = new Station("TestStation8", District.BAOAN, 2, 4);

        BusLine busLine = new BusLine("Test1");
        List<Station> list = Arrays.asList(s1, s2, s3, s4, s5);
        for (Station station : list) {
            busLine.addStation(station);
        }

        assertEquals(s2.getName(), busLine.nearestStation(s6).getName());
        assertEquals(s3.getName(), busLine.nearestStation(s7).getName());
        assertEquals(s5.getName(), busLine.nearestStation(s8).getName());


        Class<BusLine> clazz = BusLine.class;
        Method method = clazz.getDeclaredMethod("printStation");
        assertTrue("BusLine.nearestStation is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question02_task16_busline_nearestStation2() {
        checkField();

        Station s1 = new Station("TestStation1", District.NANSHAN, 22, 120);
        Station s2 = new Station("TestStation2", District.NANSHAN, 23, 120);
        Station s3 = new Station("TestStation3", District.NANSHAN, 23, 120.9);
        Station s4 = new Station("TestStation4", District.NANSHAN, 22, 120.1);
        Station s5 = new Station("TestStation5", District.NANSHAN, 22, 121);

        BusLine busLine = new BusLine("Test2");
        List<Station> list = Arrays.asList(s1, s2, s3, s4, s5);
        for (Station station : list) {
            busLine.addStation(station);
        }

        assertEquals(s4.getName(), busLine.nearestStation(s1).getName());
        assertNotEquals(s2.getName(), busLine.nearestStation(s1).getName());

        assertEquals(s4.getName(), busLine.nearestStation(s5).getName());
        assertEquals(s3.getName(), busLine.nearestStation(s2).getName());
    }

    @Test
    public void question02_task17_busline_ratioOfDistrict1() throws NoSuchMethodException {
        checkField();

        BusLine busLine = new BusLine("74");

        List<Station> list = testStations1();
        for (Station station : list) {
            busLine.addStation(station);
        }

        assertEquals(0.122, busLine.ratioOfDistrict(District.NANSHAN), 0.001);

        assertEquals(0, busLine.ratioOfDistrict(District.FUTIAN), 0.00001);
        assertEquals(0, busLine.ratioOfDistrict(District.LUOHU), 0.00001);
        assertEquals(0, busLine.ratioOfDistrict(District.LONGGANG), 0.00001);
        assertEquals(0, busLine.ratioOfDistrict(District.LONGHUA), 0.00001);


        Class<BusLine> clazz = BusLine.class;
        Method method = clazz.getDeclaredMethod("ratioOfDistrict", District.class);
        assertTrue("BusLine.ratioOfDistrict is not public", Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void question02_task18_busline_ratioOfDistrict2() {
        checkField();

        BusLine busLine = new BusLine("38");

        List<Station> list = testStations2();
        for (Station station : list) {
            busLine.addStation(station);
        }

        assertEquals(0.137, busLine.ratioOfDistrict(District.FUTIAN), 0.001);
        assertEquals(0.130, busLine.ratioOfDistrict(District.LUOHU), 0.001);

        assertEquals(0, busLine.ratioOfDistrict(District.BAOAN), 0.00001);

    }

    @Test
    public void question02_task99_bonus_busline_addStation() throws InvocationTargetException, IllegalAccessException {
        checkField();

        Class<BusLine> clazz = BusLine.class;
        Method method = null;
        try {
            method = clazz.getDeclaredMethod("addStation", Station.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("no addStation(Station, int) found. bonus is not finish.");
        }
        assertTrue("BusLine.addStation is not public", Modifier.isPublic(method.getModifiers()));

        Station s1 = new Station("Taoyuan", District.NANSHAN, 22.5325, 113.92472);
        Station s2 = new Station("Nanshan", District.NANSHAN, 22.52975, 113.9304333);
        Station s3 = new Station("Xili", District.NANSHAN, 22.58065, 113.95444);
        Station s4 = new Station("University Town", District.NANSHAN, 22.58194, 113.965);
        Station s5 = new Station("Changlingpi", District.NANSHAN, 22.59917, 114.01111);
        Station s6 = new Station("Chaguang", District.NANSHAN, 22.57694, 113.94944);

        BusLine busLine = new BusLine("74");

        busLine.addStation(s1);
        busLine.addStation(s4);
        method.invoke(busLine, s2, 1);
        assertEquals(s2.getName(), busLine.getHead().getNext().getNext().getName());

        method.invoke(busLine, s3, 2);
        assertEquals(s3.getName(), busLine.getHead().getNext().getNext().getNext().getName());

        busLine.addStation(s5);
        assertEquals(s5.getName(), busLine.getHead().getNext().getNext().getNext().getNext().getNext().getName());
    }

    private void checkField() {
        try {
            question02_task07_busline_fields();
        } catch (Throwable e) {
            fail("Please finish task07 first");
        }
    }

    private List<Station> testStations1() {
        return Arrays.asList(
                new Station("Changlingpi", District.NANSHAN, 22.59917, 114.01111),
                new Station("University Town", District.NANSHAN, 22.58194, 113.965),
                new Station("Xili", District.NANSHAN, 22.58065, 113.95444),
                new Station("Chaguang", District.NANSHAN, 22.57694, 113.94944),
                new Station("Taoyuan", District.NANSHAN, 22.5325, 113.92472),
                new Station("Nanshan", District.NANSHAN, 22.52975, 113.9304333)
        );
    }

    private List<Station> testStations2() {
        return Arrays.asList(
                new Station("Xiangmei North", District.FUTIAN, 22.55389, 114.03361),
                new Station("Jingtian", District.FUTIAN, 22.55361, 114.04333),
                new Station("Lianhua West", District.FUTIAN, 22.54778, 114.04972),
                new Station("Civic Center", District.FUTIAN, 22.54444, 114.05667),
                new Station("Convention and Exhibition Center Station", District.FUTIAN, 22.5375, 114.05472),
                new Station("Gangxia", District.FUTIAN, 22.53778, 114.06306),
                new Station("Huaqiang South", District.FUTIAN, 22.543333, 114.093333),
                new Station("Ludancun", District.LUOHU, 22.5415417, 114.115694),
                new Station("Renmin South", District.LUOHU, 22.5418528, 114.1244611),
                new Station("Luohu", District.LUOHU, 22.53111, 114.11833)
        );
    }
}
