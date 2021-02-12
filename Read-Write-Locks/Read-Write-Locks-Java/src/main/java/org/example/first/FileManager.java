package org.example.first;

import org.example.first.lock.CustomReadersWriterLock;
import org.example.first.lock.ReadersWriterLock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.Scanner;

public class FileManager {
  private final File file;
  private final String fileName;
  private final CustomReadersWriterLock lock = new ReadersWriterLock();

  public FileManager(String fileName) {
    this.fileName = fileName;
    file = new File(fileName);
  }

  public void findPhoneByName(String name) throws FileNotFoundException {
    lock.readLock().lock();
    System.out.println("Searching for record with name: " + name);
    Optional<Data> record = findRecord(name, null);
    System.out.println(
        "Result for record with name "
            + name
            + " : "
            + record.map(Data::getPhone).orElse("none found"));
    lock.readLock().unlock();
  }

  public void findNameByPhone(String phone) throws FileNotFoundException {
    lock.readLock().lock();
    System.out.println("Searching for record with phone: " + phone);
    Optional<Data> record = findRecord(null, phone);
    System.out.println(
        "Result for record with phone "
            + phone
            + " : "
            + record.map(Data::getName).orElse("none found"));
    lock.readLock().unlock();
  }

  private Optional<Data> findRecord(String name, String phone) throws FileNotFoundException {
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] currentData = line.split(" - ");
      String currentName = currentData[0];
      String currentPhone = currentData[1];
      if (currentName.equals(name) || currentPhone.equals(phone)) {
        scanner.close();
        return Optional.of(new Data(currentName, currentPhone));
      }
    }
    scanner.close();
    return Optional.empty();
  }

  public void addRecord(Data record) throws IOException {
    String line;
    if (file.length() == 0) line = record.toString();
    else line = System.lineSeparator() + record.toString();

    lock.writeLock().lock();
    System.out.println("Add record: " + record);
    Files.write(Paths.get(fileName), line.getBytes(), StandardOpenOption.APPEND);
    lock.writeLock().unlock();
  }

  public void removeRecord(Data record) throws IOException {
    lock.writeLock().lock();
    System.out.println("Start removing record: " + record);
    boolean foundRecord = false;
    String tempFileName = "src/main/resources/temp.txt";
    File tempFile = new File(tempFileName);
    Scanner scanner = new Scanner(file);
    FileWriter writer = new FileWriter(tempFile);
    BufferedWriter out = new BufferedWriter(writer);
    boolean firstLine = true;
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.equals(record.toString())) {
        foundRecord = true;
      } else {
        if (!firstLine) out.newLine();
        out.write(line);
      }
      firstLine = false;
    }
    out.close();
    scanner.close();
    Files.move(Paths.get(tempFileName), Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
    if (foundRecord) System.out.println("Successfully removed the record");
    else System.out.println("Record has not been found.");
    lock.writeLock().unlock();
  }
}
