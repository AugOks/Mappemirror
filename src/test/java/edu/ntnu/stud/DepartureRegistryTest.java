package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartureRegistryTest {
  /**
   * <h6>Positive test</h6>
   * <p>
   * Test creation of registry with valid objects, this test verifies that the class behaves as it
   * should when given valid objects and when they are manipulated according to the requirements
   * specification.</p>
   * <p>
   * When creating departures in the registry we only use valid parameters for the objects and
   * only try to manipulate them in valid ways.
   * When manipulating the registry will test the following functionality:
   * <ul>
   *   <li>Find a train by a given train number</li>
   *   <li>Adding a train with valid parameters</li>
   *   <li>Adding a departure with a delay.</li>
   *   <li>Remove departures before the current time</li>
   *   <li>Find a list of departures based on destination</li>
   *   <li>Get a list of departures sorted by departure time.</li>
   * </ul>
   * </p>
   */
  private DepartureRegistry depRegistry;
  private List<TrainDeparture> destinationList;
  private List<TrainDeparture> culledTimelist;
  private List<TrainDeparture> sortedTimeList;


  @BeforeEach
  public void fillRegistryWithTestingObjects() {
    depRegistry = new DepartureRegistry();
    destinationList = new ArrayList<>();
    culledTimelist = new ArrayList<>();
    sortedTimeList = new ArrayList<>();
    LocalTime time1 = LocalTime.of(10, 30);
    LocalTime time2 = LocalTime.of(10, 50);
    LocalTime time3 = LocalTime.of(10, 40);
    LocalTime time4 = LocalTime.of(10, 20);
    int trainNumber1 = 20;
    int trainNumber2 = 30;
    int trainNumber3 = 320;
    int trainNumber4 = 520;
    TrainDeparture test1 = new TrainDeparture(time1, "L1", trainNumber1, 1,
        "Drammen");
    TrainDeparture test2 = new TrainDeparture(time2, "L2", trainNumber2, 2,
        "oslo");
    TrainDeparture test3 = new TrainDeparture(time3, "L3", trainNumber3, 3,
        "oslo");
    TrainDeparture test4 = new TrainDeparture(time4, "L4", trainNumber4, 4,
        "Gøteborg");
    depRegistry.addDeparture(test1);
    depRegistry.addDeparture(test2);
    depRegistry.addDeparture(test3);
    depRegistry.addDeparture(test4);

    destinationList.add(test3);
    destinationList.add(test2);

    culledTimelist.add(test3);
    culledTimelist.add(test2);

    sortedTimeList.add(test4);
    sortedTimeList.add(test1);
    sortedTimeList.add(test3);
    sortedTimeList.add(test2);
  }

  /**
   * <h6>Positive test</h6>
   * <p>
   * Testing that when given valid inputs, the departure registry acts as expected.
   */
  //TODO: Refactor to their own methods
  @Test
  public void testDepartureRegistryWithValidInput() {
    TrainDeparture test1 = new TrainDeparture(LocalTime.of(10, 55), "L1",
        604, 1, "Drammen");
    TrainDeparture test2 = new TrainDeparture(LocalTime.of(10, 55), "L1",
        601, 1, "Drammen");

    // Asserts that adding trains with and without delay works as expected.
    assertTrue(depRegistry.addDeparture(test1));
    // Find train that was just added.
    assertEquals(test1, depRegistry.findTrainByNumber(604));
    assertTrue(depRegistry.addDeparture(new TrainDeparture(LocalTime.of(10, 55), "L1",
        601, 1, "Drammen", LocalTime.of(0, 10))));
  }

  /**
   *Asserts that the list containing all departures to a given destination is equal to a
   * predefined list of expected departures.
   */
  @Test
  public  void testDeparturesToDestinationMatchesList(){
    for (int i = 0; i < destinationList.size(); i++) {
      assertEquals(destinationList.get(i).getTrainNr(), depRegistry.findDepByDest("oslo")
          .get(i).getTrainNr());
    }
  }

  /**
   * Asserts that the list of sorted departures based on the registry is equal to the predefined
   * sorted list.
   */
  @Test
  public void testSortedDepartureList(){
    List<TrainDeparture> list = new ArrayList<>(depRegistry.getSortedDeparturesByTime());
    for (int i = 0; i < sortedTimeList.size(); i++) {
      assertEquals(sortedTimeList.get(i).getTrainNr(), list.get(i).getTrainNr());
    }
  }

  /**
   * Asserts that the culled registry is equal to the expected culled departure list based on the
   * time given.
   */
  @Test
  public void testCulledRegistryBasedOnTimeCutOff(){
    depRegistry.removePreviousDeparture(LocalTime.of(10, 40));
    Iterator<TrainDeparture> it = depRegistry.getAllDepartures();
    int index = 0;
    while (it.hasNext()) {
      TrainDeparture temp = it.next();
      assertEquals(culledTimelist.get(index).getTrainNr(), temp.getTrainNr());
      index++;
    }
  }

  /**
   * <h6>Negative test</h6>
   *
   */
  @Test
  public void testDepartureRegistryWithInvalidInput() {
    try {
      TrainDeparture test1 = new TrainDeparture(LocalTime.of(-1, 0), "",
          -1, 0, null);

      fail();
    }catch (DateTimeException e) {
      assertTrue(true);

    }

    TrainDeparture test2 = new TrainDeparture(LocalTime.of(0, 0), "",
        -1, 0, null, LocalTime.of(0, 0));
    assertFalse(depRegistry.addDeparture(test2));
    assertFalse(depRegistry.addDeparture(new TrainDeparture(LocalTime.of(0, 0), "", -1,
        0, null, LocalTime.of(0, 0))));
  }

}