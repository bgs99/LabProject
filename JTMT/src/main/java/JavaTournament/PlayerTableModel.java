package JavaTournament;

import bgs99c.shared.FighterStats;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PlayerTableModel extends AbstractTableModel {
	private String[] columnNames = new String []{"Name", "Rank", "Score", "Avg. rank"};
	private List<PlayerModel> data = new ArrayList<>();
	public void addRow(PlayerModel model){
		data.add(model);
		this.fireTableDataChanged();
	}

	public PlayerModel getRow(int i){
	    return data.get(i);
    }
	@Override
	public String getColumnName(int i){
        return columnNames[i];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
	    PlayerModel player = data.get(row);
		switch (column){
            case 0:
                return player.getName();
            case 1:
                return player.getRank();
            case 2:
                return player.getScore();
            case 3:
                return player.getARank();
            default:
                return null;
        }
	}
}
