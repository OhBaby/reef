package uk.ac.imperial.vazels.reef.client.workloads;

import java.util.LinkedList;

public class Workload {

  public LinkedList<String> actors = new LinkedList<String>();
  public final String name;

  public Workload(final String name) {
    this.name = name;
  } 
}
