package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class randMap2 {
	public class Exit {
		private int direction;// 0 for left, 1 for up, 2 for down, 3 for
								// right
		private int posX, posY;
		private Room cur;

		private Exit(int direction, int x1, Room cur) {
			this.direction = direction;
			this.cur = cur;
			if (direction == 0) {
				posX = 0;
				posY = x1;
			} else if (direction == 1) {
				posX = x1;
				posY = 0;
			} else if (direction == 2) {
				posX = x1;
				posY = cur.height - 1;
			} else if (direction == 3) {
				posX = cur.width - 1;
				posY = x1;
			}
		}

		private Exit(int direction) {
			this.direction = direction;
		}

		private boolean match(int[][] tileMap, Room room, Exit a, int x, int y) {
			if (a.direction == 3 - direction) {
				int height = room.height;
				int width = room.width;
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						int X = i + x;
						int Y = j + y;
						if (X > tileMap.length - 1 || Y > tileMap[0].length - 1 || X < 0 || Y < 0 || roomMap[X][Y] != 0)
							return false;
					}
				}
				return true;
			}
			return false;
		}
	}

	private class Room {
		private ArrayList<Exit> list;
		private int[][] tileMap;// 0 for background, 1 for normal blocks
		private int height, width;
		private int index;
		private int roomX, roomY;
		private boolean corridor;

		private Room(int width, int height) {
			this.height = height;
			this.width = width;
			list = new ArrayList<Exit>();
		}

		private void pos(int x, int y) {
			this.roomX = x;
			this.roomY = y;
		}

		private void debugExit(ArrayList<Exit> a) {
			list = a;
		}

		private void tile(int[][] tileMap, ArrayList<Exit> list, boolean corridor) {
			this.corridor = corridor;
			this.tileMap = tileMap;
			this.list = list;
		}

		private void assignIndex(int index) {
			this.index = index;
		}

		private void scramExits() {
			if (list.size() == 0) {
				throw new NullPointerException();
			}
			if (list.size() == 1) {
				return;
			}
			for (int i = 0; i < list.size() - 1; i++) {
				int rand = (int) (Math.random() * (list.size() - i - 1) + i);
				Exit a = list.get(i);
				list.set(i, list.get(rand));
				list.set(rand, a);
			}
		}

		private ArrayList<Exit> directionalElimination(int direction) {
			int size = list.size();
			if (size == 0) {
				throw new NullPointerException();
			}
			ArrayList<Exit> ret = new ArrayList<Exit>();
			for (int i = 0; i < size; i++) {
				ret.add(list.get(i));
			}
			for (int i = size - 1; i >= 0; i--) {
				if (size == 0) {
					break;
				}
				if (direction == list.get(i).direction) {
					ret.remove(i);
				}
			}
			return ret;
		}
	}

	private int[][] roomMap; // positive = index for the room, -1 means exit
	private ArrayList<Room> rooms;
	private ArrayList<Room> adds = new ArrayList<Room>();
	private ArrayList<Integer> notAddable = new ArrayList<Integer>();
	public static int width;
	public static int height;

	public randMap2(int level) {
		// this.level = level;
		notAddable.add(1);
		rooms = new ArrayList<Room>();
		// plains();
		// caves();
		// mountain();
		// safari();
		// ocean();
		// desert();
		// catacomb();
		// gw();
		// heaven();
		// hell();
	}

	private void merge(ArrayList<Integer> x, ArrayList<Integer> y, int[][] rm, int ax, int ay) {
		if (x.size() != y.size())
			throw new IllegalArgumentException();
		for (int i = x.size() - 1; i >= 0; i--) {
			int x1 = x.get(i) - ax;
			int y1 = y.get(i) - ay;
			int vx = roomMap.length - x1;
			int vy = roomMap[0].length - y1;
			outerloop: for (int a = 0; a < vx; a++) {
				for (int b = 0; b < vy; b++) {
					int X = a + x1;
					int Y = b + y1;
					if (roomMap[X][Y] != 0) {
						if (rm[a][b] != 0) {
							x.remove(i);
							y.remove(i);
							break outerloop;

						}
					}
				}
			}
		}
		int rand = (int)(x.size() * Math.random());
		int width = rm.length + roomMap.length - ax;
		int length = rm[0].length + roomMap[0].length - ay;
		int[][] romMap = new int[width][length];
		int x1 = x.get(rand) - ax;
		int y1 = y.get(rand) - ay;
		for (int i = 0; i < roomMap.length; i++) {
			for (int j = 0; j < roomMap[0].length; j++) {
				romMap[i][j] = roomMap[i][j];
			}
		}
		for (int i = x1; i < rm.length + x1; i++) {
			for (int j = y1; j < rm[0].length + y1; j++) {
				romMap[i][j] = rm[i - x1][j - y1];
			}
		}
	}

	private boolean inMap(int x, int y) {
		if (x < 0 || y < 0 || x > roomMap.length - 1 || y > roomMap[0].length - 1) {
			return false;
		}
		return true;
	}

	public void adds(ArrayList<Room> adds) {

		this.adds = adds;
	}

	private void plainsAdds() {
		Room rand = new Room(3, 3);
		ArrayList<Exit> norand = new ArrayList<Exit>();
		norand.add(new Exit(2, 1, rand));
		rand.debugExit(norand);
		adds.add(rand);
	}

	private void safariAdds() {
		Room rand = new Room(5, 5);
		ArrayList<Exit> norand = new ArrayList<Exit>();
		norand.add(new Exit(2, 1, rand));
		rand.debugExit(norand);
		adds.add(rand);

		Room rand2 = new Room(5, 5);
		ArrayList<Exit> norand2 = new ArrayList<Exit>();
		norand2.add(new Exit(1, 1, rand));
		rand2.debugExit(norand2);
		adds.add(rand2);

		Room rand3 = new Room(5, 5);
		ArrayList<Exit> norand3 = new ArrayList<Exit>();
		norand3.add(new Exit(0, 1, rand));
		rand3.debugExit(norand3);
		adds.add(rand3);

		Room rand4 = new Room(5, 5);
		ArrayList<Exit> norand4 = new ArrayList<Exit>();
		norand4.add(new Exit(3, 1, rand));
		rand4.debugExit(norand4);
		adds.add(rand4);
	}

	private void cavesAdds() {
		Room rand = new Room(5, 5);
		ArrayList<Exit> norand = new ArrayList<Exit>();
		norand.add(new Exit(2, 2, rand));
		rand.debugExit(norand);
		adds.add(rand);

		Room rand2 = new Room(5, 5);
		ArrayList<Exit> norand2 = new ArrayList<Exit>();
		norand2.add(new Exit(1, 2, rand2));
		rand2.debugExit(norand2);
		adds.add(rand2);

		Room rand3 = new Room(5, 5);
		ArrayList<Exit> norand3 = new ArrayList<Exit>();
		norand3.add(new Exit(0, 2, rand3));
		rand3.debugExit(norand3);
		adds.add(rand3);

		Room rand4 = new Room(5, 5);
		ArrayList<Exit> norand4 = new ArrayList<Exit>();
		norand4.add(new Exit(3, 2, rand4));
		rand4.debugExit(norand4);
		adds.add(rand4);
	}

	private void mountainAdds() {
		Room rand = new Room(5, 5);
		ArrayList<Exit> norand = new ArrayList<Exit>();
		norand.add(new Exit(0, 2, rand));
		rand.debugExit(norand);
		adds.add(rand);

		Room rand2 = new Room(5, 5);
		ArrayList<Exit> norand2 = new ArrayList<Exit>();
		norand2.add(new Exit(0, 2, rand));
		rand2.debugExit(norand2);
		adds.add(rand2);

		Room rand3 = new Room(5, 5);
		ArrayList<Exit> norand3 = new ArrayList<Exit>();
		norand3.add(new Exit(3, 2, rand));
		rand3.debugExit(norand3);
		adds.add(rand3);

		Room rand4 = new Room(5, 5);
		ArrayList<Exit> norand4 = new ArrayList<Exit>();
		norand4.add(new Exit(3, 2, rand));
		rand4.debugExit(norand4);
		adds.add(rand4);
	}

	private void oceanAdds() {
		for (int i = 0; i < 6; i++) {
			Room rand = new Room(4 + (int) (Math.random() * 3), 5);
			ArrayList<Exit> norand = new ArrayList<Exit>();
			norand.add(new Exit(0, 2, rand));
			rand.debugExit(norand);
			adds.add(rand);

			Room rand2 = new Room(4 + (int) (Math.random() * 3), 5);
			ArrayList<Exit> norand2 = new ArrayList<Exit>();
			norand2.add(new Exit(3, 2, rand2));
			rand2.debugExit(norand2);
			adds.add(rand2);
		}
	}

	public void plains() {
		// • Plains (very standard, 0) - This will be a one-way path from the
		// start
		// to end. No tricks anywhere, mostly (70-90%) flat and very
		// straightforward.
		// pass in rooms with manipulated exits so that this will work,
		// else the exits will go off pretty wildly.
		ArrayList<Room> sizes = new ArrayList<Room>();
		ArrayList<Exit> normal = new ArrayList<Exit>();
		Room start = new Room(5, 5);
		normal.add(new Exit(3, 2, start));
		start.debugExit(normal);

		Room end = new Room(5, 5);
		ArrayList<Exit> normal2 = new ArrayList<Exit>();
		normal2.add(new Exit(0, 2, end));
		end.debugExit(normal2);

		int roomNum = 30;
		double verticalNum = 0;
		for (int j = 0; j < roomNum; j++) {
			verticalNum += Math.random() * 0.1;
		}
		verticalNum = (int) verticalNum;
		for (int i = 0; i < roomNum - 4 * verticalNum; i++) {
			// int random = (int) (Math.random() * 3);
			Room rand = new Room(4 + (int) (Math.random() * 3), 4 + (int) (Math.random() * 3));
			ArrayList<Exit> norand = new ArrayList<Exit>();
			// 10% chance up or down
			norand.add(new Exit(0, 2, rand));
			norand.add(new Exit(3, 2, rand));
			rand.debugExit(norand);
			sizes.add(rand);
		}
		for (int i = 0; i < verticalNum; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					Room rand = new Room(4 + (int) (Math.random() * 3), 4 + (int) (Math.random() * 3));
					ArrayList<Exit> norand = new ArrayList<Exit>();
					norand.add(new Exit(j + 1, rand.width / 2, rand));
					norand.add(new Exit(k * 3, rand.height / 2, rand));
					rand.debugExit(norand);
					sizes.add(rand);
				}
			}
		}
		sizes = scramRooms(sizes);
		plainsGen(sizes, start, end);
	}

	private void plainsGen(ArrayList<Room> sizes, Room start, Room end) {
		roomMap = new int[150][17];
		Exit a = start.list.get(0);
		assignRoom(start, 0, 5);
		ArrayList<Exit> solution = plainsRecurse(a, sizes);
		notAddable.add(solution.size());
		for (int i = 0; i < solution.size(); i++) {
			Exit b = solution.get(i);
			roomMap[b.posX + b.cur.roomX][b.posY + b.cur.roomY] = -1;
		}
		plainsAdds();
		sideAdds(adds);
	}

	private ArrayList<Exit> plainsRecurse(Exit pre, ArrayList<Room> sizes) {
		// same for many
		ArrayList<Exit> ret = new ArrayList<Exit>();
		if (sizes.size() == 0) {
			ret.add(pre);
			return ret;
		}
		int currentX = pre.posX + pre.cur.roomX;
		int currentY = pre.posY + pre.cur.roomY;
		int var = exitCorrection(pre, currentX, currentY);
		currentX = var / 1000;
		currentY = var % 1000;
		outerloop: while (true) {
			for (int j = 0; j < sizes.size(); j++) {
				Room curRoom = sizes.get(j);
				curRoom.scramExits();
				for (int k = 0; k < curRoom.list.size(); k++) {
					Exit a = curRoom.list.get(k);
					int newRX = currentX - a.posX;
					int newRY = currentY - a.posY;
					if (currentX >= a.posX && currentY >= a.posY && pre.match(roomMap, curRoom, a, newRX, newRY)) {
						ArrayList<Exit> temp = curRoom.directionalElimination(3 - pre.direction);
						assignRoom(curRoom, newRX, newRY);
						sizes.remove(j);
						ArrayList<Exit> rec = plainsRecurse(temp.get(0), sizes);
						if (rec != null) {
							ret.add(pre);
							ret.addAll(ret.size(), rec);
							break outerloop;
						}
						sizes.add(j, curRoom);
						removeRoom(curRoom, newRX, newRY, a);
					}
				}
			}
			// System.out.println("AM I USEFUL??");
			return null;
		}
		return ret;
	}

	public void caves() {
		// • Cave (no weather effects, limited vision, 1) - Start at the bottom,
		// make your way up (about 50/50 vertical-horizontal). Have small
		// branches
		// (1 room, preferably medium or small rooms).
		ArrayList<Room> sizes = new ArrayList<Room>();
		ArrayList<Exit> normal = new ArrayList<Exit>();
		Room start = new Room(5, 5);
		normal.add(new Exit(3, 2, start));
		start.debugExit(normal);
		Room end = new Room(5, 5);
		ArrayList<Exit> normal2 = new ArrayList<Exit>();
		normal.add(new Exit(3, 2, start));
		end.debugExit(normal2);
		for (int i = 0; i < 10; i++) {
			for (int k = 0; k < 2; k++) {
				Room rand = new Room(4 + (int) (Math.random() * 3), 4 + (int) (Math.random() * 3));
				ArrayList<Exit> norand = new ArrayList<Exit>();
				if (k == 0) {
					norand.add(new Exit(0, rand.height / 2, rand));
					norand.add(new Exit(1, rand.width / 2, rand));
				} else {
					norand.add(new Exit(2, rand.width / 2, rand));
					norand.add(new Exit(3, rand.height / 2, rand));
				}
				rand.debugExit(norand);
				sizes.add(rand);
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int k = 0; k < 2; k++) {
				Room rand = new Room(4 + (int) (Math.random() * 3), 4 + (int) (Math.random() * 3));
				ArrayList<Exit> norand = new ArrayList<Exit>();
				if (k == 0) {
					norand.add(new Exit(0, rand.height / 2, rand));
					norand.add(new Exit(2, rand.width / 2, rand));
				} else {
					norand.add(new Exit(1, rand.width / 2, rand));
					norand.add(new Exit(3, rand.height / 2, rand));
				}
				rand.debugExit(norand);
				sizes.add(rand);
			}
		}
		sizes = scramRooms(sizes);
		cavesGen(sizes, start, end);
	}

	private void cavesGen(ArrayList<Room> sizes, Room start, Room end) {
		roomMap = new int[55][55];
		Exit a = start.list.get(0);
		assignRoom(start, 0, roomMap.length - 6);
		ArrayList<Exit> solution = plainsRecurse(a, sizes);
		notAddable.add(solution.size());
		for (int i = 0; i < solution.size(); i++) {
			Exit b = solution.get(i);
			roomMap[b.posX + b.cur.roomX][b.posY + b.cur.roomY] = -1;
		}
		cavesAdds();
		sideAdds(adds);
	}

	public void safari() {
		// • Safari (lots of animals, 1) - This will have one obviously main
		// path
		// but also has 1-2 room detours along the way. More horizontal than
		// vertical
		plains();
		safariAdds();
		sideAdds(adds);
	}

	public void mountain() {
		ArrayList<Room> mainBranch = new ArrayList<Room>();
		ArrayList<Exit> normal = new ArrayList<Exit>();
		Room start = new Room(8, 8);
		normal.add(new Exit(3, 2, start));
		start.debugExit(normal);
		Room end = new Room(8, 8);
		ArrayList<Exit> normal2 = new ArrayList<Exit>();
		normal2.add(new Exit(0, 2, end));
		end.debugExit(normal2);
		int shortNum = 7;
		int longNum = 7;
		for (int i = 0; i < shortNum; i++) {
			Room rand = new Room(8, 6 + (int) (Math.random() * 3));
			ArrayList<Exit> norand = new ArrayList<Exit>();
			norand.add(new Exit(0, 2, rand));
			norand.add(new Exit(3, 2, rand));
			rand.debugExit(norand);
			mainBranch.add(rand);
		}
		for (int i = 0; i < longNum; i++) {
			Room rand = new Room(8, 15 + (int) (Math.random() * 3));
			ArrayList<Exit> norand = new ArrayList<Exit>();
			norand.add(new Exit(0, 2 + (int) (Math.random() * 10), rand));
			norand.add(new Exit(3, 2 + (int) (Math.random() * 10), rand));
			rand.debugExit(norand);
			mainBranch.add(rand);
		}
		mainBranch = scramRooms(mainBranch);
		mountainGen(mainBranch, start, end);
	}

	private void mountainGen(ArrayList<Room> mainB, Room start, Room end) {
		roomMap = new int[130][40];
		Exit a = start.list.get(0);
		assignRoom(start, 0, roomMap[0].length / 2);
		ArrayList<Exit> solution = plainsRecurse(a, mainB);
		notAddable.add(solution.size());
		for (int i = 0; i < solution.size(); i++) {
			Exit b = solution.get(i);
			roomMap[b.posX + b.cur.roomX][b.posY + b.cur.roomY] = -1;
		}
		mountainAdds();
		sideAdds(adds);
	}

	public void desert() {
		// • Desert (hidden treasure and traps?, 2) - This will have an obvious
		// main
		// path (above ground, pretty horizontal) home, but a large branch that
		// leads underground to multiple smaller branches. Getting out is
		// deliberately difficult, since you are now in a tomb.

		ArrayList<Room> sizes = new ArrayList<Room>();
		ArrayList<Exit> normal = new ArrayList<Exit>();
		Room start = new Room(5, 5);
		normal.add(new Exit(3, 2, start));
		start.debugExit(normal);

		Room end = new Room(5, 5);
		ArrayList<Exit> normal2 = new ArrayList<Exit>();
		normal2.add(new Exit(0, 2, end));
		end.debugExit(normal2);

		int roomNum = 15;
		for (int i = 0; i < roomNum; i++) {
			// int random = (int) (Math.random() * 3);
			Room rand = new Room(4 + (int) (Math.random() * 3), 4 + (int) (Math.random() * 3));
			ArrayList<Exit> norand = new ArrayList<Exit>();
			// 10% chance up or down
			norand.add(new Exit(0, 2, rand));
			norand.add(new Exit(3, 2, rand));
			rand.debugExit(norand);
			sizes.add(rand);
		}
		sizes = scramRooms(sizes);
		desertGen(sizes, start, end);
	}

	private void desertGen(ArrayList<Room> sizes, Room start, Room end) {
		roomMap = new int[80][17];
		Exit a = start.list.get(0);
		assignRoom(start, 0, 5);
		ArrayList<Exit> solution = plainsRecurse(a, sizes);
		notAddable.add(solution.size());
		for (int i = 0; i < solution.size(); i++) {
			Exit b = solution.get(i);
			roomMap[b.posX + b.cur.roomX][b.posY + b.cur.roomY] = -1;
		}
	}

	public void ocean() {
		// • Ocean (water everywhere with a tide, 3) - Start at the bottom of
		// the
		// ocean or something. Very large rooms underwater with some small rooms
		// connected to them. Home is at the surface somewhere.
		ArrayList<Exit> normal = new ArrayList<Exit>();
		Room start = new Room(8, 8);
		normal.add(new Exit(3, 3, start));
		start.debugExit(normal);

		ArrayList<Exit> mid = new ArrayList<Exit>();
		Room middle = new Room(40, 60);
		mid.add(new Exit(0, 10, middle));
		mid.add(new Exit(3, 50, middle));
		middle.debugExit(mid);

		Room end = new Room(8, 8);
		ArrayList<Exit> normal2 = new ArrayList<Exit>();
		normal2.add(new Exit(0, 3, end));
		end.debugExit(normal2);

		notAddable.add(3);

		roomMap = new int[80][80];
		assignRoom(start, 10, 15);
		assignRoom(middle, 18, 8);
		assignRoom(end, 58, 58);
		oceanAdds();
		sideAdds(adds);
	}

	public void catacomb() {
		// • Catacombs (maze-like, 4) - lots of branches of the same length,
		// home is
		// locked behind a door and at the end of one of them. Minimap/compass
		// does
		// not reveal home initially unless you buy the map item.
		
	}

	public void gw() {
		// • Great Wilderness (other 7 maps put together with 3 keys required to
		// get
		// home, 5) - it'll probably help if you begin at a map that leads to
		// all
		// the different places, and you move there by exiting out of one of the
		// 7
		// exits on the map. Then you'll automatically spawn at a different map,
		// where you go to get the key. 3 ways to do this: getting the key
		// teleports
		// you back, exit is behind you, or both.
	}

	public void heaven() {
		// • Heaven/Hell (Guaranteed at least one special item, selectable after
		// killing all creatures and finishing at perilous health or not
		// touching
		// any animal or loot and finishing at perilous health. Alternative:
		// just be
		// at full health) - You fall, you die in a sea of flames (or hit the
		// ground). Reach the elevator back to the surface instead of home.
		// Mostly
		// horizontal with a few branches (like cave); the challenges are
		// fighting
		// off the enemies and death if you fall off. In Hell, everything tries
		// to
		// kill you. In heaven, everything tries to kill you after you move the
		// special item.
	}

	public void hell() {

	}

	private void sideAdds(ArrayList<Room> adds) {
		for (int k = 0; k < adds.size(); k++) {
			ArrayList<Integer> xp = new ArrayList<Integer>();
			ArrayList<Integer> yp = new ArrayList<Integer>();
			Room z = adds.get(k);
			Exit a = adds.get(k).list.get(0);
			for (int j = 0; j < roomMap[0].length; j++) {
				for (int i = 0; i < roomMap.length; i++) {
					boolean good = true;
					inner: for (int y = 0; y < notAddable.size(); y++) {
						if (roomMap[i][j] == notAddable.get(y)) {
							good = false;
							break inner;
						}
					}
					if (good) {
						Exit b = null;
						int x = i;
						int y = j;
						if (roomMap[x][y] > 0) {
							int sides = 0;
							int newX = x - a.posX;
							int newY = y - a.posY;
							if (x < 1 || roomMap[x - 1][y] == 0) {
								b = new Exit(0);
								newX--;
								sides++;
							}
							if (x >= roomMap.length - 1 || roomMap[x + 1][y] == 0) {
								b = new Exit(3);
								newX++;
								sides++;
							}
							if (y < 1 || roomMap[x][y - 1] == 0) {
								b = new Exit(1);
								newY--;
								sides++;
							}
							if (y >= roomMap[0].length - 1 || roomMap[x][y + 1] == 0) {
								b = new Exit(2);
								newY++;
								sides++;
							}
							if (sides == 1) {
								if (x >= z.width && y >= z.height) {
									if (b.match(roomMap, z, a, newX, newY)) {
										xp.add(newX);
										yp.add(newY);
									}
								}
							}
						}
					}

				}
			}
			int rand = (int) (Math.random() * xp.size());
			assignRoom(z, xp.get(rand), yp.get(rand));
		}
	}

	private int exitCorrection(Exit pre, int x, int y) {
		if (pre.direction == 0) {
			x -= 1;
		} else if (pre.direction == 1) {
			y -= 1;
		} else if (pre.direction == 2) {
			y += 1;
		} else if (pre.direction == 3) {
			x += 1;
		}
		return 1000 * x + y;
	}

	private void removeRoom(Room room, int x, int y, Exit a) {
		if (x < 0 || y < 0)
			throw new IndexOutOfBoundsException();
		int height = room.height;
		int width = room.width;
		rooms.remove(room);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int X = i + x;
				int Y = j + y;
				if (roomMap[X][Y] == 0) {
					throw new IllegalArgumentException("wrong indices");
				} else {
					roomMap[X][Y] = 0;
				}
			}
		}
		room.pos(0, 0);
	}

	public void assignRoom(Room room, int x, int y) {
		if (x < 0 || y < 0)
			throw new IndexOutOfBoundsException();
		int height = room.height;
		int width = room.width;
		rooms.add(rooms.size(), room);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int X = i + x;
				int Y = j + y;
				if (roomMap[X][Y] != 0) {
					System.out.println("X, Y " + X + ", " + Y);
					throw new IllegalArgumentException("collision");
				} else {
					roomMap[X][Y] = rooms.indexOf(room) + 1;
				}
			}
		}
		room.pos(x, y);
	}

	private ArrayList<Room> scramRooms(ArrayList<Room> list) {
		if (list.size() == 0) {
			throw new NullPointerException();
		}
		if (list.size() == 1) {
			return list;
		}
		for (int i = 0; i < list.size() - 1; i++) {
			int rand = (int) (Math.random() * (list.size() - i - 1) + i);
			Room a = list.get(i);
			list.set(i, list.get(rand));
			list.set(rand, a);
		}
		return list;
	}

	// scaled
	public void draw(Graphics2D g) {
		// g.drawString("hfewoifjeoi", 20, 20);
		double horSc = 800 / roomMap.length;
		double verSc = 800 / roomMap[0].length;
		for (int i = 0; i < roomMap.length; i++) {
			for (int j = 0; j < roomMap[0].length; j++) {
				if (roomMap[i][j] >= 0) {
					g.setPaint(new Color((int) (255 * (1 - (double) roomMap[i][j] / rooms.size())),
							(int) (255 * (1 - (double) roomMap[i][j] / rooms.size())),
							(int) (255 * (1 - (double) roomMap[i][j] / rooms.size()))));
					g.fillRect((int) (i * horSc), (int) (j * verSc), (int) (horSc), (int) (verSc));
				} else {
					g.setPaint(new Color(255, 0, 0));
					g.fillRect((int) (i * horSc), (int) (j * verSc), (int) (horSc), (int) (verSc));
				}
			}
		}
		g.setPaint(new Color(100, 255, 100));
		for (int z = 0; z < adds.size(); z++) {
			for (int i = adds.get(z).roomX; i < adds.get(z).roomX + adds.get(z).width; i++) {
				for (int j = adds.get(z).roomY; j < adds.get(z).roomY + adds.get(z).height; j++) {
					g.fillRect((int) (i * horSc), (int) (j * verSc), (int) (horSc), (int) (verSc));
				}
			}
		}
		// not scaled
		// double horSc = 10;
		// double verSc = 10;
		// for (int i = 0; i < roomMap.length; i++) {
		// for (int j = 0; j < roomMap[0].length; j++) {
		// if (roomMap[i][j] > 0) {
		// g.drawRect((int) (i * horSc), (int) (j * verSc), (int) (horSc), (int)
		// (verSc));
		// } else if (roomMap[i][j] != 0) {
		// g.fillRect((int) (i * horSc), (int) (j * verSc), (int) (horSc), (int)
		// (verSc));
		// }
		// }
		// }
		// g.setPaint(new Color(100, 255, 100));
		// for (int z = 0; z < adds.size(); z++) {
		// for (int i = adds.get(z).roomX; i < adds.get(z).roomX +
		// adds.get(z).width; i++) {
		// for (int j = adds.get(z).roomY; j < adds.get(z).roomY +
		// adds.get(z).height; j++) {
		// g.fillRect((int) (i * horSc), (int) (j * verSc), (int) (horSc), (int)
		// (verSc));
		// }
		// }
		// }
	}

	public String toString() {
		String ret = "";
		for (int j = 0; j < roomMap[0].length; j++) {
			for (int i = 0; i < roomMap.length; i++) {
				if (roomMap[i][j] < 10 && roomMap[i][j] >= 0) {
					ret += "0" + roomMap[i][j] + " ";
				} else {
					ret += roomMap[i][j] + " ";
				}
			}
			ret += "\n";
		}
		return ret;
	}

	public static void main(String[] args) {
		// new randMap2(0);
		randMap2 a = new randMap2(1);
		String b = a.toString();
		JFrame window = new JFrame("ok");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		System.out.println(b);
	}
}