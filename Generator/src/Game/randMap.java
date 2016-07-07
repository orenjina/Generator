package Game;
//comment section for randMap2
// public void roomTileGen(ArrayList<Room> sizes, Room start, Room end) {
// roomMap = new int[60][60];
// assignRoom(start, (roomMap.length) / 2, (roomMap[0].length) / 2,
// start.randExit());
// for (int i = 0; i < sizes.size(); i++) {
// sizes.get(i).scramExits();
// boolean pass = false;
// for (int j = i; j < sizes.size(); j++) {
// if (pass) {
// break;
// }
// for (int k = 0; k < sizes.get(j).list.size(); k++) {
// Exit a = sizes.get(j).list.get(k);
// if (curX >= a.posX && curY >= a.posY
// && curPosition.match(roomMap, sizes.get(j), a, curX - a.posX, curY -
// a.posY)) {
// sizes.get(j).directionalElimination(3 - curPosition.direction);
// int z = (int) (Math.random() * sizes.get(j).list.size());
// assignRoom(sizes.get(j), curX - a.posX, curY - a.posY,
// sizes.get(j).list.get(z));
// pass = true;
// Room ab = sizes.get(z);
// sizes.set(j, sizes.remove(z));
// sizes.add(z, ab);
// break;
// }
// }
// }
// }
// end.scramExits();
//
// for (Exit a : end.list) {
// if (curPosition.match(roomMap, end, a, curX - a.posX, curY - a.posY)) {
// assignRoom(end, curX - a.posX, curY - a.posY, a);
// System.out.println("It is run!");
// break;
// }
// }
// }
import java.util.ArrayList;

public class randMap {
	private class Room {
		private class Exit {
			private int direction;// 0 for left, 1 for up, 2 for down, 3 for
									// right
			private int startPosition;
			private int endPosition;

			private Exit(int direction, int startPosition, int endPosition) {
				this.direction = direction;
				this.startPosition = startPosition;
				this.endPosition = endPosition;
			}

			private Exit match() {
				return new Exit(3 - direction, startPosition, endPosition);
			}
		}

		private ArrayList<Exit> list;
		private int[][] tileMap;// 0 for background, 1 for normal blocks
		private int type; // 0 1x1, 1 2x1, 2 1x2, 3 2x2
		private int index;
		private int x, y;

		private Room(int index, int type) {
			this.index = index;
			this.type = type;
		}

		private Room(int index, int type, int x, int y) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.index = index;
			list = new ArrayList<Exit>();
		}

		private void tile(int[][] tileMap) {
			this.tileMap = tileMap;
			// generate exit list
			for (int i = 0; i < tileMap.length; i++) {
				if (tileMap[i][0] == 0) {
					int j = i;
					while (j < tileMap.length && tileMap[j][0] == 0) {
						j++;
					}
					list.add(new Exit(1, i, j));
				}
			}
			for (int i = 0; i < tileMap.length; i++) {
				if (tileMap[i][tileMap.length - 1] == 0) {
					int j = i;
					while (j < tileMap.length && tileMap[j][tileMap.length - 1] == 0) {
						j++;
					}
					list.add(new Exit(2, i, j));
				}
			}
			for (int i = 0; i < tileMap[0].length; i++) {
				if (tileMap[0][i] == 0) {
					int j = i;
					while (j < tileMap[0].length && tileMap[0][j] == 0) {
						j++;
					}
					list.add(new Exit(0, j, i));
				}
			}
			for (int i = 0; i < tileMap[0].length; i++) {
				if (tileMap[tileMap[0].length][i] == 0) {
					int j = i;
					while (j < tileMap[0].length && tileMap[tileMap[0].length - 1][j] == 0) {
						j++;
					}
					list.add(new Exit(3, j, i));
				}
			}
		}
	}

	private Room[][] roomMap;
	private int level, roomTotal;
	private int curRoom;

	public randMap(int level) {
		this.level = level;
		roomTotal = level / 7 + 12;
	}

	public void roomTileGen() {
		int ind = 0;
		int sqRoom = roomTotal / 12;
		int recRoom = roomTotal / 8;
		for (int i = 0; i < roomTotal / 12; i++) {
			if (Math.random() > 0.5) {
				sqRoom++;
			}
		}
		for (int i = 0; i < roomTotal / 8; i++) {
			if (Math.random() > 0.5) {
				recRoom++;
			}
		}
		int regularRoom = roomTotal - sqRoom * 4 - recRoom * 2;
		int horRoom = (int) (recRoom * (Math.random() / 2));
		int verRoom = recRoom - horRoom;
		int[] keyInd = new int[4];
		keyInd[0] = sqRoom;
		keyInd[1] = keyInd[0] + horRoom;
		keyInd[2] = keyInd[1] + verRoom;
		keyInd[3] = keyInd[2] + regularRoom;
		// last room will be regular
		// first room will be regular
		ind = keyInd[3];
		keyInd[3]--;
		int x = 0;
		int y = 0;
		int type = 0;
		roomMap[x][y] = new Room(keyInd[3], type);
		keyInd[3]--;
		while (keyInd[keyInd.length - 1] > 0) {
			ArrayList<Room> posSpace = posPlace(type, x, y, keyInd);
			int size = posSpace.size();
			int rand = (int) (Math.random() * keyInd[3]);
			for (int i = 0; i < keyInd.length; i++) {
				if (rand < keyInd[i]) {
					rand = i;
				}
			}
			
		}
	}

	private void locRoomAdd(int x, int y, int[] keyInd, ArrayList<Room> ret) {
		// single
		if (roomMap[x][y] == null)
			ret.add(new Room(keyInd[3], 0, x, y));
		// horizontal
		if (x > 0 && roomMap[x - 1][y] == null)
			ret.add(new Room(keyInd[1], 1, x - 1, y));
		if (x < roomMap.length - 1 && roomMap[x + 1][y] == null)
			ret.add(new Room(keyInd[1], 1, x, y));
		// vertical
		if (y < roomMap[0].length - 1 && roomMap[x][y + 1] == null)
			ret.add(new Room(keyInd[2], 2, x, y));
		if (y > 0 && roomMap[x][y - 1] == null)
			ret.add(new Room(keyInd[2], 2, x, y - 1));
		// square box
		if (boxHelper(x, y))
			ret.add(new Room(keyInd[0], 3, x, y));
		if (boxHelper(x - 1, y - 1))
			ret.add(new Room(keyInd[0], 3, x - 1, y - 1));
		if (boxHelper(x - 1, y))
			ret.add(new Room(keyInd[0], 3, x - 1, y));
		if (boxHelper(x, y - 1))
			ret.add(new Room(keyInd[0], 3, x, y - 1));
	}

	private boolean boxHelper(int x, int y) {
		if (y < 0 || x < 0 || roomMap[x][y] != null) {
			return false;
		}
		if (x < roomMap.length - 1 || roomMap[x + 1][y] != null) {
			return false;
		}
		if (y < roomMap[0].length - 1 || roomMap[x][y + 1] != null) {
			return false;
		}
		if (roomMap[x - 1][y - 1] != null) {
			return false;
		}
		return true;
	}

	private ArrayList<Room> posPlace(int type, int x, int y, int[] keyInd) {
		ArrayList<Room> ret = new ArrayList<Room>();
		if (type == 0) {
			locRoomAdd(x - 1, y, keyInd, ret);
			locRoomAdd(x + 1, y, keyInd, ret);
			locRoomAdd(x, y - 1, keyInd, ret);
			locRoomAdd(x, y + 1, keyInd, ret);
		}
		if (type == 1) {
			locRoomAdd(x - 1, y, keyInd, ret);
			locRoomAdd(x + 2, y, keyInd, ret);
			locRoomAdd(x, y - 1, keyInd, ret);
			locRoomAdd(x, y + 1, keyInd, ret);
			locRoomAdd(x + 1, y - 1, keyInd, ret);
			locRoomAdd(x + 1, y + 1, keyInd, ret);

		}
		if (type == 2) {
			locRoomAdd(x - 1, y, keyInd, ret);
			locRoomAdd(x + 1, y, keyInd, ret);
			locRoomAdd(x, y - 1, keyInd, ret);
			locRoomAdd(x, y + 2, keyInd, ret);
			locRoomAdd(x - 1, y + 1, keyInd, ret);
			locRoomAdd(x + 1, y + 1, keyInd, ret);
		}
		if (type == 3) {
			locRoomAdd(x - 1, y, keyInd, ret);
			locRoomAdd(x, y - 1, keyInd, ret);
			locRoomAdd(x + 1, y - 1, keyInd, ret);
			locRoomAdd(x - 1, y + 1, keyInd, ret);
			locRoomAdd(x + 2, y, keyInd, ret);
			locRoomAdd(x, y + 2, keyInd, ret);
			locRoomAdd(x + 1, y + 2, keyInd, ret);
			locRoomAdd(x + 2, y + 1, keyInd, ret);
		}
		return ret;
	}
}