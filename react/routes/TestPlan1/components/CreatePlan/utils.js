/* eslint-disable import/prefer-default-export */
export function autoSelect(dataSet, treeMap) {
  dataSet.forEach((record) => {
    // 下面是自动选中相关的代码
    const folderId = record.get('folderId');
    const caseId = record.get('caseId');          
    const folder = treeMap.get(folderId);         
    // 如果文件夹被选中，而且这一项没有被取消勾选，就选中
    if (folder.checked) {
      // 如果已经被选中，就不进行下面
      if (record.isSelected) {
        return;
      }
      const { unSelectedCases = [], selectedCases = [] } = folder;
      // 未选中不包括，或者选中包括，就选中
      if ((unSelectedCases.length > 0 && !unSelectedCases.includes(caseId)) || (selectedCases.length > 0 && selectedCases.includes(caseId))) {
        record.set('source', 'auto');
        dataSet.select(record);
      }
    } else {
      // 如果没被选中，就不进行下面
      if (!record.isSelected) {
        return;
      }
      dataSet.unSelect(record);
    }
  });
}
