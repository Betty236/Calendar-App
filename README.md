# Calendar Project

A lightweight, Java‑Swing desktop application for personal scheduling that bundles
appointments, reminders, and holidays into an intuitive month‑, day‑ and list‑based view.

---

## ✨ Key Features

| Area | Highlights |
|------|------------|
| **Event types** | *Appointments* (with location & notes) · *Reminders* (fire at a specific time) · *Holidays* |
| **Views** | Month grid, single‑day timeline, searchable event / reminder sidebars |
| **Interaction** | Double‑click to add, drag‑and‑drop to move, colour tagging, scroll‑smooth custom list panels |
| **Validation** | Built‑in checks (e.g. prevents dates outside 2024 in current prototype) |
| **Storage layer** | In‑memory `EventManager` class holds `ArrayList`s for each event type (easy to swap for file/DB) |
| **Extensible design** | Clean model (`Event` → `Appointment` / `Reminder` / `Holiday`), pluggable `EventHandler` interface |

---

## 📂 Project Layout

```
Calendar Project/
├─ src/                ← "readable" Java sources
│  ├─ CalendarApp.java     (application entry point, GUI bootstrapper)
│  ├─ WinDayView.java      (scrollable hour‑by‑hour view)
│  ├─ EventListView.java   (sidebar list with custom scrollbar UI)
│  ├─ Event*, Reminder*, … (domain classes & dialogs)
│  └─ ...                 
├─ bin/                ← compiled .class files (Eclipse default output)
├─ .project, .classpath ← Eclipse metadata
└─ .settings/          ← Eclipse code‑style prefs
```

---

## ⚙️ Requirements

| What | Version |
|------|---------|
| **JDK** | 17 or newer (uses `java.time` API & switch expressions) |
| **Build tool** | *Optional* – project ships as a plain Eclipse workspace, but Maven/Gradle setup is trivial (see below) |
| **OS** | Any OS with a compatible JVM (Windows, macOS, Linux) |

---

## 🚀 Getting Started

### 1. Run from an IDE (Eclipse / IntelliJ / VS Code)

1. **Import** the project as *Existing Java Project* (Eclipse) or *Gradle‑less Maven* module (IntelliJ).  
2. Ensure the execution JDK is **17+**.  
3. Hit **Run ➜ CalendarApp.main()**.  

### 2. Compile & run by hand

```bash
# From repo root
javac -d bin $(find ./src -name "*.java")
java -cp bin CalendarApp
```

### 3. Add Maven (optional but recommended)

```bash
mvn archetype:generate -DgroupId=com.example.calendar \
                       -DartifactId=calendar-app -DinteractiveMode=false
# copy src/ into generated folder, then:
mvn javafx:run        # or simply mvn package && java -jar target/*.jar
```

---

## 🖥️ Usage Tips

* **Navigate months** – click ◄/► arrows or use ←/→ keys.  
* **Create event** – double‑click a day cell (month view) or an empty hour slot (day view).  
* **Edit/Delete** – right‑click an event bubble → *Edit* / *Remove*.  
* **Reminders** pop an alert at the chosen `remindTime`. Keep the app running in the background.  

---

## 🛣️ Roadmap Ideas

- Persist events to **JSON** or **SQLite** so data survives restarts  
- Dark‑mode & responsive scaling for HiDPI displays  
- Recurring events (RRULE) & multi‑year support (remove 2024 hard‑check)  
- iCal / Google Calendar import & export  
- Unit tests (JUnit 5) and CI workflow

---

## 📜 License

This project is currently **unlicensed** – meaning *all rights reserved*.  
Before publishing or forking, add an open‑source license (MIT, Apache‑2.0, etc.) or contact the author.
